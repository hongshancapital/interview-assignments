package com.wb.http_server.context;

import com.wb.http_server.context.holder.ServletHolder;
import com.wb.http_server.cookie.Cookie;
import com.wb.http_server.exception.ServletNotFoundException;
import com.wb.http_server.listener.RequestAndSessionListener;
import com.wb.http_server.listener.HttpSessionListener;
import com.wb.http_server.listener.ServletRequestListener;
import com.wb.http_server.listener.event.HttpSessionEvent;
import com.wb.http_server.listener.event.ServletRequestEvent;
import com.wb.http_server.request.Request;
import com.wb.http_server.response.Response;
import com.wb.http_server.servlet.Servlet;
import com.wb.http_server.session.HttpSession;
import com.wb.http_server.session.IdleSessionCleaner;
import com.wb.http_server.util.PathMatcher;
import com.wb.http_server.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.wb.http_server.constant.ContextConstant.*;

/**
 * @author bing.wang
 * @date 2021/1/15
 */

@Slf4j
public class ServletContext {
    /**
     * 别名->类名
     * 一个Servlet类只能有一个Servlet别名，一个Servlet别名只能对应一个Servlet类
     */
    private Map<String, ServletHolder> servlets;
    /**
     * 一个Servlet可以对应多个URL，一个URL只能对应一个Servlet
     * URL Pattern -> Servlet别名
     */
    private Map<String, String> servletMapping;


    /**
     * 监听器们
     */
    private List<ServletRequestListener> servletRequestListeners;
    private List<HttpSessionListener> httpSessionListeners;


    /**
     * 整个应用对应的session们
     */
    private Map<String, HttpSession> sessions;
    private IdleSessionCleaner idleSessionCleaner;

    private PathMatcher matcher;


    /**
     * 域
     */
    private Map<String, Object> attributes;

    public ServletContext() {
        init();
    }

    /**
     * 由URL得到对应的一个Servlet实例
     *
     * @param url
     * @return
     * @throws ServletNotFoundException
     */
    public Servlet mapServlet(String url) throws ServletNotFoundException {
        // 1、精确匹配

        String servletAlias = servletMapping.get(url);
        if (servletAlias != null) {
            return initAndGetServlet(servletAlias);
        }
        // 2、路径匹配
        List<String> matchingPatterns = new ArrayList<>();
        Set<String> patterns = servletMapping.keySet();
        for (String pattern : patterns) {
            if (matcher.match(pattern, url)) {
                matchingPatterns.add(pattern);
            }
        }

        if (!matchingPatterns.isEmpty()) {
            Comparator<String> patternComparator = matcher.getPatternComparator(url);
            Collections.sort(matchingPatterns, patternComparator);
            String bestMatch = matchingPatterns.get(0);
            return initAndGetServlet(servletMapping.get(bestMatch));
        }
        return initAndGetServlet(DEFAULT_SERVLET_ALIAS);
    }

    /**
     * 初始化并获取Servlet实例，如果已经初始化过则直接返回
     *
     * @param servletAlias
     * @return
     * @throws ServletNotFoundException
     */
    private Servlet initAndGetServlet(String servletAlias) throws ServletNotFoundException {
        ServletHolder servletHolder = servlets.get(servletAlias);
        if (servletHolder == null) {
            throw new ServletNotFoundException();
        }
        if (servletHolder.getServlet() == null) {
            try {
                Servlet servlet = (Servlet) Class.forName(servletHolder.getServletClass()).newInstance();
                servlet.init();
                servletHolder.setServlet(servlet);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return servletHolder.getServlet();
    }


    /**
     * 应用初始化
     */
    private void init() {
        this.servlets = new HashMap<>();
        this.servletMapping = new HashMap<>();
        this.attributes = new ConcurrentHashMap<>();
        this.sessions = new ConcurrentHashMap<>();
        this.idleSessionCleaner = new IdleSessionCleaner();
        this.idleSessionCleaner.start();
        this.servletRequestListeners = new ArrayList<>();
        this.httpSessionListeners = new ArrayList<>();
        this.matcher = new PathMatcher();
        config();

    }

    /**
     * 应用关闭前被调用
     */
    public void destroy() {
        servlets.values().forEach(servletHolder -> {
            if (servletHolder.getServlet() != null) {
                servletHolder.getServlet().destroy();
            }
        });
    }

    public void addServlet(String servletName, String mapping,String servletClass){
        this.servlets.put(servletName,new ServletHolder(servletClass));
        this.servletMapping.put(mapping,servletName);
    }

    private void config() {
        this.servlets.put("DefaultServlet", new ServletHolder("com.wb.http_server.servlet.impl.DefaultServlet"));
        this.servletMapping.put("/", "DefaultServlet");
        servletRequestListeners.add(new RequestAndSessionListener());
        httpSessionListeners.add(new RequestAndSessionListener());
    }


    /**
     * 获取session
     *
     * @param JSESSIONID
     * @return
     */
    public HttpSession getSession(String JSESSIONID) {
        return sessions.get(JSESSIONID);
    }

    /**
     * 创建session
     *
     * @param response
     * @return
     */
    public HttpSession createSession(Response response) {
        HttpSession session = new HttpSession(UUIDUtil.uuid());
        sessions.put(session.getId(), session);
        response.addCookie(new Cookie("JSESSIONID", session.getId()));
        HttpSessionEvent httpSessionEvent = new HttpSessionEvent(session);
        for (HttpSessionListener listener : httpSessionListeners) {
            listener.sessionCreated(httpSessionEvent);
        }
        return session;
    }

    /**
     * 销毁session
     *
     * @param session
     */
    public void invalidateSession(HttpSession session) {
        sessions.remove(session.getId());
        afterSessionDestroyed(session);
    }

    /**
     * 清除空闲的session
     * 由于ConcurrentHashMap是线程安全的，所以remove不需要进行加锁
     */
    public void cleanIdleSessions() {
        for (Iterator<Map.Entry<String, HttpSession>> it = sessions.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, HttpSession> entry = it.next();
            if (Duration.between(entry.getValue().getLastAccessed(), Instant.now()).getSeconds() >= DEFAULT_SESSION_EXPIRE_TIME) {
                log.info("该session {} 已过期", entry.getKey());
                afterSessionDestroyed(entry.getValue());
                it.remove();
            }
        }
    }

    private void afterSessionDestroyed(HttpSession session) {
        HttpSessionEvent httpSessionEvent = new HttpSessionEvent(session);
        for (HttpSessionListener listener : httpSessionListeners) {
            listener.sessionDestroyed(httpSessionEvent);
        }
    }

    public void afterRequestCreated(Request request) {
        ServletRequestEvent servletRequestEvent = new ServletRequestEvent(this, request);
        for (ServletRequestListener listener : servletRequestListeners) {
            listener.requestInitialized(servletRequestEvent);
        }
    }

    public void afterRequestDestroyed(Request request) {
        ServletRequestEvent servletRequestEvent = new ServletRequestEvent(this, request);
        for (ServletRequestListener listener : servletRequestListeners) {
            listener.requestDestroyed(servletRequestEvent);
        }
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

}
