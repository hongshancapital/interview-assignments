package com.shorturl.config;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ruohanpan on 21/3/23.
 */
@Aspect
@Component
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* com.shorturl.service.ServerVersionService.updateVersion())")
    public void updateVersionPointcut() {}

    @AfterReturning(returning = "version", pointcut = "updateVersionPointcut()")
    public void updateVersionAfterReturn(Object version) {
        logger.info("get a new version {}", version);
    }

    @AfterThrowing(throwing = "e", pointcut = "updateVersionPointcut()")
    public void updateVersionAfterThrowing(Throwable e) {
        logger.warn("", e);
    }
}
