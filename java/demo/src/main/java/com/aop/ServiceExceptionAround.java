package com.aop;

import com.common.Result;
import com.common.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
public class ServiceExceptionAround {
    @Pointcut("execution(public * com.controller.*.*(..))")
    public void serviceException() {
    }

    @Around("serviceException()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ServiceException e) {
            return new Result<>(10000, e.getMessage());
        }
    }
}
