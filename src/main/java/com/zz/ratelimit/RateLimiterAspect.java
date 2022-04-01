package com.zz.ratelimit;

import com.zz.exception.BusinessException;
import com.zz.exception.code.SystemErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * 限流控制注解
 *
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    @Autowired
    private RateLimiterManager manager;

    @Pointcut("@annotation(com.zz.ratelimit.RateLimiterAnno)")
    public void rateLimit() {
    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint pjp) throws Throwable {
        //获取限流配置
        RateLimiterAnno rateLimiterAnno = getAnnoConfig(pjp);
        if (rateLimiterAnno != null) {
            RateLimiterConfig config = buildConfig(rateLimiterAnno);
            if (manager.isValid(config) && !manager.tryAcquire(config)) {
                throw new BusinessException(SystemErrorCodeEnum.SYS_002);
            }
        }

        return pjp.proceed();
    }

    /**
     * 构建配置
     *
     * @param rateLimiterAnno
     * @return
     */
    private RateLimiterConfig buildConfig(RateLimiterAnno rateLimiterAnno) {
        RateLimiterConfig config = new RateLimiterConfig();
        config.setKey(rateLimiterAnno.key());
        config.setQPS(rateLimiterAnno.QPS());
        config.setTimeout(rateLimiterAnno.timeout());
        config.setTimeUnit(rateLimiterAnno.timeUnit());
        return config;
    }

    /**
     * 获取限流配置
     *
     * @param pjp
     * @return
     * @throws Exception
     */
    private RateLimiterAnno getAnnoConfig(ProceedingJoinPoint pjp) throws Exception {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        return AnnotationUtils.findAnnotation(ms.getMethod(), RateLimiterAnno.class);
    }
}
