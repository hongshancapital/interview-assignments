package com.example.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: eric
 * @date: 2021-07-21
 * @time: 14:40
 */
public class PointcutConfiguration {
    @Pointcut("execution(public * com.example.service..*.*(..))")
    public void handleLog() {
    }

    @Pointcut("execution(public * com.example.controller..*.*(..))")
    public void accessService() {
    }
}
