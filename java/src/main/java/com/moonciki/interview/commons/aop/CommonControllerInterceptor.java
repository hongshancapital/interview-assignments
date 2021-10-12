package com.moonciki.interview.commons.aop;

import com.moonciki.interview.commons.constant.GlobalConstant;
import com.moonciki.interview.commons.tools.ControllerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 统一controller 请求拦截异常处理类
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class CommonControllerInterceptor extends ControllerInterceptor {

    private final String POINT_CUT = "execution( * " + GlobalConstant.Base.base_package + ".controller..*.*(..))";

    @Pointcut(POINT_CUT)
    public void controllerPoint(){}

    @Around("controllerPoint()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Exception{

        Object returnObj = invokePoint(joinPoint);

        return returnObj;
    }

}
