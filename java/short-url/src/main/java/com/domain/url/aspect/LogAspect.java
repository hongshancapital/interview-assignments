package com.domain.url.aspect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 接口日志打印
 */
@Slf4j
@Aspect
@Configuration
public class LogAspect {
    @Pointcut("execution(* com.domain.url.web.controller..*.*(..))")
    private void controllerAspect() {
    }

    @Before(value = "controllerAspect()")
    public void invokeBefore(JoinPoint point) {
        String realClassName = getRealClassName(point);
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put("traceId", traceId);
        log.info("invoke class: {}, execute method: {}, requestParams: {}, headers: {}", realClassName, getMethodName(point), getRequestParams(point), getRequestHeaderInfo());
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnValue")
    public void invokeAfter(JoinPoint point, Object returnValue) {
        String realClassName = getRealClassName(point);
        log.info("invoke class: {}, execute method: {}", realClassName, getMethodName(point));
        MDC.clear();
    }

    private String getRealClassName(JoinPoint point) {
        return point.getTarget().getClass().getName();
    }

    private String getMethodName(JoinPoint point) {
        return point.getSignature().getName();
    }

    private List<Object> getRequestParams(JoinPoint point) {
        return Lists.newArrayList(point.getArgs());
    }

    private Map<String, String> getRequestHeaderInfo() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(sra).getRequest();
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, String> headerMap = Maps.newHashMap();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            if (!"cookie".equals(name)) {
                headerMap.put(name, request.getHeader(name));
            }
        }
        return headerMap;
    }
}

