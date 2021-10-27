package com.example.demo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class WebLogAspect {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** 两个..代表所有子目录，最后括号里的两个..代表所有参数 */
    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void logPointCut() {}

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 记录下请求内容
        log.info(
                "\n请求地址:{}\nHTTP 方法:{}\n参数:{}\n",
                request.getRequestURL().toString(),
                request.getMethod(),
                Arrays.toString(joinPoint.getArgs()));
        log.info(
                "CLASS#METHOD: {}#{}\n",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    /**
     * returning的值和doAfterReturning的参数名一致
     *
     * @param ret returning的值
     * @throws Throwable 异常
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("\n返回值:{}\n", OBJECT_MAPPER.writeValueAsString(ret));
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 方法返回值
        Object o = pjp.proceed();
        log.info("\n耗时:{}ms\n", (System.currentTimeMillis() - startTime));
        return o;
    }
}
