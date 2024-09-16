package com.xiaoxi666.tinyurl.filter;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/10
 * @Version: 1.0
 * @Description: 用于链路追踪，方便问题排查
 */
@Configuration
@WebFilter(filterName = "traceIdFilter", urlPatterns = "/*")
@Order(0)
public class TraceIdFilter implements Filter {
    /**
     * 日志跟踪标识
     */
    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        MDC.put(TRACE_ID, UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MDC.clear();
    }
}