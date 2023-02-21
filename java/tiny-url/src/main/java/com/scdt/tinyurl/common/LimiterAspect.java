package com.scdt.tinyurl.common;

import com.google.common.util.concurrent.RateLimiter;
import com.scdt.tinyurl.annotation.Limiter;
import com.scdt.tinyurl.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class LimiterAspect {

    RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    @Pointcut("@annotation(com.scdt.tinyurl.annotation.Limiter)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取目标方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Limiter limiter = method.getAnnotation(Limiter.class);
        rateLimiter.setRate(limiter.accessRate());

        // 获取令牌桶中的一个令牌，最多等待1秒
        if (rateLimiter.tryAcquire(1, 1, TimeUnit.SECONDS)) {
            return point.proceed();
        } else {
            log.error(limiter.name() + " 接口并发量过大执行限流");
            throw new GlobalException(ErrorCode.NETWORK_OVERFLOW_ERROR);
        }
    }

}
