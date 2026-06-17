package com.example.aspect;

import com.example.bean.mdc.MdcContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Description: 日志拦截
 *
 * @author: eric
 * @date: 2021/07/21
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LogInterceptor {

    @Around("com.example.aspect.PointcutConfiguration.handleLog()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //当存在traceId时，不做任何处理
        if(Objects.nonNull(MdcContext.getTraceId())) {
            return proceedingJoinPoint.proceed();
        }
        //traceId不存在，初始化赋值
        MdcContext.setTraceId();
        Object result = proceedingJoinPoint.proceed();
        //清除本次请求的traceId
        MdcContext.clear();
        return result;
    }
}
