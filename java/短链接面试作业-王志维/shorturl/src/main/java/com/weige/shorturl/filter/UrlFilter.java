package com.weige.shorturl.filter;

import com.weige.shorturl.common.Storage;
import com.weige.shorturl.config.FilterConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @ClassName UrlFilter
 * @Description 请求拦截器，用作接收短连接请求时重定向到长链接
 * @Author zwwang14
 * @Date 2022/1/20 16:20
 * @Version 1.0
 */
@Component
public class UrlFilter implements Filter {

     Log log = LogFactory.getLog(UrlFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponseWrapper httpResponse = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        String path=httpRequest.getRequestURI();
        if(path.indexOf("/t/")>0){
            String longUrl = Storage.find(path.replace("/interview",""));
            if (StringUtils.isEmpty(longUrl)){
                log.info("短链接:"+path+"  未添加到缓存");
            }else {
                //httpRequest.getRequestDispatcher(longUrl).forward(servletRequest,servletResponse);
                log.info("从短链接:"+path+"  跳转到了:"+longUrl);
                httpResponse.sendRedirect(longUrl);
            }
        }
        else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
        return;
    }

    @Override
    public void destroy() {

    }
}
