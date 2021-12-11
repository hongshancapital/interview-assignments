package com.scdt.china.shorturl.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author zhouchao
 * @Date 2021/11/26 14:14
 * @Description 基于切面打印请求日志
 **/
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.scdt.china.shorturl.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : {}" , request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}" , request.getMethod());
        log.info("IP : {}" , request.getRemoteAddr());
        log.info("CLASS_METHOD : {}" , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : {}" , Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : {}" , ret);
        log.info("SPEND TIME : {}" , (System.currentTimeMillis() - startTime.get()));
    }
}
