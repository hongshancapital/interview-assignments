package com.skg.domain.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author smith skg
 * @Date 2021/10/13 17:29
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class HttpRequestAspect {

    @Around("@annotation(com.skg.domain.demo.anno.ApiLog)")
    public Object cachedList(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        StringBuilder builder = new StringBuilder();
        //获取目标方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        //拼接valueKey
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            builder.append(arg);
            if (i == args.length - 1) {
                break;
            }
            builder.append(":");
        }
        String url = request.getRequestURI();
        String valueKey = builder.toString();
        log.info("请求地址：{}入参：{}",url,valueKey);
        Object result = proceedingJoinPoint.proceed();
        log.info("请求地址：{}出参：{}",url,result);
        return result;
    }

}
