package com.liuxiang.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 耗时统计
 * @author liuxiang6
 * @date 2022-01-07
 **/
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    private static final String START_TIME = "_start_time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(START_TIME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();

        log.info("{} {} 耗时:{}", ServletUtil.getClientIP(request), request.getRequestURI(), endTime - startTime);
    }
}
