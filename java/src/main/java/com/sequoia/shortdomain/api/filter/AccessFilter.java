package com.sequoia.shortdomain.api.filter;

import com.sequoia.shortdomain.common.ShortDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "*", filterName = "AccessFilter")
public class AccessFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        //必要的安全检查，防止任意调用，造成接口不安全隐患
        //系统授权的appID
        String appID=servletRequest.getParameter("appID");
        //应携带根据appID加密后的token
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        //根据timestamp,保持一段时间内过期
        String timestamp=httpRequest.getHeader("timestamp");
        String token=httpRequest.getHeader("token");
        //具体校验代码，非本项目核心，先省略
        //用于日志追踪
        String requestId = UUID.randomUUID().toString();
        MDC.put("REQUESTID", requestId);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServletException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw  new ShortDomainException(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw  new ShortDomainException(e.getMessage());
        } finally {
            MDC.remove("REQUESTID");

        }
    }

}
