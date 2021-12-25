package com.sequoia.domain.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 切面记录接口调用耗时
 *
 */
@Aspect
@Component
public class RequestAuditAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAuditAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controller() {
    }

    @Before("controller()")
    public void doBeforeController(JoinPoint joinPoint) {
        TimeStampContext.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "response", pointcut = "controller()")
    public void doAfterController(JoinPoint joinPoint, Object response) {
        long cost = System.currentTimeMillis() - TimeStampContext.get();
        LOGGER.info("invoke cost: {}", cost);
        TimeStampContext.remove();
    }

    private static class TimeStampContext {
        private static final ThreadLocal<Long> context = new ThreadLocal<>();

        public static void set(Long user) {
            context.set(user);
        }

        public static Long get() {
            return context.get();
        }

        public static void remove() {
            context.remove();
        }
    }
}
