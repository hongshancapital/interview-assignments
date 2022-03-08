package com.zxp.demo.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 过滤器拦截指定短链接地址
 * @author: zxp
 * @date: Created in 2021/12/13 15:02
 */
@Component
@WebFilter(filterName="UrlFilter",urlPatterns="/*")
public class UrlFilter extends OncePerRequestFilter {

    private static final String SHORT_URL_PREFIX = "/a.cn/";

    private static final  String SHORT_URL_KEY_PREFIX = "short_url_hash_key_";

    private static final Logger log = LoggerFactory.getLogger(UrlFilter.class);

    private StringRedisTemplate stringRedisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = uri.substring(contextPath.length());

        if(!url.startsWith(SHORT_URL_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (stringRedisTemplate == null){
            WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(
                    request.getSession().getServletContext());
            stringRedisTemplate = (StringRedisTemplate) ac.getBean("stringRedisTemplate");
        }

        String longUrl = stringRedisTemplate.opsForValue().get(SHORT_URL_KEY_PREFIX + url.substring(url.lastIndexOf("/") + 1));

        if (StringUtils.isBlank(longUrl)){
            log.warn("对应url {},没有找到原链接", url);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write("{\"flag\":\"error\",\"msg\":\"抱歉，原链接已过期销毁\",\"data\":null}");
            out.flush();
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "    window.location.href = '"+ longUrl +"'" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
        out.flush();
    }



}
