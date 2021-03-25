package com.shorturl.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ruohanpan on 21/3/23.
 */
@Aspect
@Component
public class ControllerLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    @Pointcut("execution(* com.shorturl.controllers.*Controller.*(..))" +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void requestRecord() {}

    // TODO: 21-3-24 delete
    @Before("requestRecord()")
    public void record() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Real-IP");
        String path = request.getServletPath();
        logger.info("{} access {}", ip, path);
    }
}
