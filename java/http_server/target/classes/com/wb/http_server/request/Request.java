package com.wb.http_server.request;

import com.wb.http_server.constant.CharConstant;
import com.wb.http_server.constant.CharsetProperties;
import com.wb.http_server.context.ServletContext;
import com.wb.http_server.context.WebApplication;
import com.wb.http_server.cookie.Cookie;
import com.wb.http_server.enumeration.RequestMethod;
import com.wb.http_server.exception.RequestInvalidException;
import com.wb.http_server.exception.RequestParseException;
import com.wb.http_server.network.handler.AbstractRequestHandler;
import com.wb.http_server.request.dispatcher.RequestDispatcher;
import com.wb.http_server.request.dispatcher.impl.ApplicationRequestDispatcher;
import com.wb.http_server.session.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author bing.wang
 * @date 2021/1/15
 */

@Getter
@Setter
@Slf4j
public class Request {

    private AbstractRequestHandler requestHandler;
    private RequestMethod method;
    private String url;
    private Map<String, List<String>> params;
    private Map<String, List<String>> headers;
    private Map<String, Object> attributes;
    private ServletContext servletContext;
    private Cookie[] cookies;
    private HttpSession session;

    /**
     * 获取queryString或者body（表单格式）的键值类型的数据
     * @param key
     * @return
     */
    public String getParameter(String key) {
        List<String> params = this.params.get(key);
        if(params == null) {
            return null;
        }
        return params.get(0);
    }

    public String getHeader(String key) {
        List<String> header = this.headers.get(key);
        if(header == null) {
            return null;
        }
        return header.get(0);
    }
    
    
    /**
     * 解析HTTP请求
     * 读取请求体只能使用字节流，使用字符流读不到
     * @param data
     * @throws RequestParseException
     */
    public Request(byte[] data) throws RequestParseException, RequestInvalidException {
        this.attributes = new HashMap<>();
        String[] lines = null;
        try {
            //支持中文，对中文进行URL解码
            lines = URLDecoder.decode(new String(data, CharsetProperties.UTF_8_CHARSET), CharsetProperties.UTF_8).split(CharConstant.CRLF);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("Request读取完毕");
        log.info("请求行: {}", Arrays.toString(lines));
        if (lines.length <= 1) {
            throw new RequestInvalidException();
        }
        try {
            parseHeaders(lines);
            if (headers.containsKey("Content-Length") && !headers.get("Content-Length").get(0).equals("0")) {
                parseBody(lines[lines.length - 1]);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RequestParseException();
        }
        
        WebApplication.getServletContext().afterRequestCreated(this);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }
    
    public RequestDispatcher getRequestDispatcher(String url) {
        return new ApplicationRequestDispatcher(url);
    }
    
    /**
     * 如果请求报文中携带JSESSIONID这个Cookie，那么取出对应的session
     * 否则创建一个Session，并在响应报文中添加一个响应头Set-Cookie: JSESSIONID=D5A5C79F3C8E8653BC8B4F0860BFDBCD
     * 所有从请求报文中得到的Cookie，都会在响应报文中返回
     * 服务器只会在客户端第一次请求响应的时候，在响应头上添加Set-Cookie：“JSESSIONID=XXXXXXX”信息，
     * 接下来在同一个会话的第二第三次响应头里，是不会添加Set-Cookie：“JSESSIONID=XXXXXXX”信息的；
     * 即，如果在Cookie中读到的JSESSIONID，那么不会创建新的Session，也不会在响应头中加入Set-Cookie：“JSESSIONID=XXXXXXX”
     * 如果没有读到，那么会创建新的Session，并在响应头中加入Set-Cookie：“JSESSIONID=XXXXXXX”
     * 如果没有调用getSession，那么不会创建新的Session
     * 
     * @param createIfNotExists 如果为true，那么在不存在session时会创建一个新的session；否则会直接返回null
     * @return HttpSession
     */
    public HttpSession getSession(boolean createIfNotExists) {
        if (session != null) {
            return session;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getKey().equals("JSESSIONID")) {
                HttpSession currentSession = servletContext.getSession(cookie.getValue());
                if (currentSession != null) {
                    session = currentSession;
                    return session;
                }
            }
        }
        if (!createIfNotExists) {
            return null;
        }
        session = servletContext.createSession(requestHandler.getResponse());
        return session;
    }

    public HttpSession getSession() {
        return getSession(true);
    }

    public String getServletPath() {
        return url;
    }
    
    private void parseHeaders(String[] lines) {
        log.info("解析请求头");
        String firstLine = lines[0];
        //解析方法
        String[] firstLineSlices = firstLine.split(CharConstant.BLANK);
        this.method = RequestMethod.valueOf(firstLineSlices[0]);
        log.debug("method:{}", this.method);

        //解析URL
        String rawURL = firstLineSlices[1];
        String[] urlSlices = rawURL.split("\\?");
        this.url = urlSlices[0];
        log.debug("url:{}", this.url);

        //解析URL参数
        if (urlSlices.length > 1) {
            parseParams(urlSlices[1]);
        }
        log.debug("params:{}", this.params);

        //解析请求头
        String header;
        this.headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            header = lines[i];
            if (header.equals("")) {
                break;
            }
            int colonIndex = header.indexOf(':');
            String key = header.substring(0, colonIndex);
            String[] values = header.substring(colonIndex + 2).split(",");
            headers.put(key, Arrays.asList(values));
        }
        log.debug("headers:{}", this.headers);

        //解析Cookie

        if (headers.containsKey("Cookie")) {
            String[] rawCookies = headers.get("Cookie").get(0).split("; ");
            this.cookies = new Cookie[rawCookies.length];
            for (int i = 0; i < rawCookies.length; i++) {
                String[] kv = rawCookies[i].split("=");
                this.cookies[i] = new Cookie(kv[0], kv[1]);
            }
            headers.remove("Cookie");
        } else {
            this.cookies = new Cookie[0];
        }
        log.info("Cookies:{}", Arrays.toString(cookies));
    }

    private void parseBody(String body) {
        log.info("解析请求体");
        byte[] bytes = body.getBytes(CharsetProperties.UTF_8_CHARSET);
        List<String> lengths = this.headers.get("Content-Length");
        if (lengths != null) {
            int length = Integer.parseInt(lengths.get(0));
            log.info("length:{}", length);
            parseParams(new String(bytes, 0, Math.min(length,bytes.length), CharsetProperties.UTF_8_CHARSET).trim());
        } else {
            parseParams(body.trim());
        }
        if (this.params == null) {
            this.params = new HashMap<>();
        }
    }

    private void parseParams(String params) {
        String[] urlParams = params.split("&");
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        for (String param : urlParams) {
            String[] kv = param.split("=");
            String key = kv[0];
            String[] values = kv[1].split(",");

            this.params.put(key, Arrays.asList(values));
        }
    }
}
