package com.example.aspect;

import com.example.enums.LocationsEnum;
import com.example.util.LogUtils;
import com.example.annotation.Sl4jLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * log4j注解处理
 *
 * @author eric
 * @create 2021-07-21 1:56 下午
 */
@Aspect
@Component
public class Sl4jSurroundLog {
    public Sl4jSurroundLog() {
    }

    @Pointcut("execution(public * com.example.service..*.*(..)) && @annotation(com.example.annotation.Sl4jLogger)")
    private void anyMethod() throws Throwable {
    }

    @Around("anyMethod()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object target = pjp.getTarget();
        Method methodImpl = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        Sl4jLogger ann = (Sl4jLogger) methodImpl.getAnnotation(Sl4jLogger.class);
        Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
        Class claz = method.getReturnType();
        Map<String, String> hashMap = new HashMap();
        hashMap.put("shortClassName", method.getDeclaringClass().getSimpleName());
        hashMap.put("methodName", method.getName());
        if (this.contains(ann.locations(), LocationsEnum.BEFORE) && logger.isInfoEnabled()) {
            logger.info(LogUtils.getLogContent(ann.prefixFormat(), hashMap, pjp.getArgs()));
        }

        Object result = pjp.proceed();
        if (this.contains(ann.locations(), LocationsEnum.AFTER) && logger.isInfoEnabled() && !claz.getName().equals("void")) {
            logger.info(LogUtils.getLogContent(ann.suffixFormat(), hashMap, new Object[]{result}));
        }

        return result;
    }

    private boolean contains(LocationsEnum[] locationsEnums, LocationsEnum locationsEnum) {
        if (locationsEnum != null && locationsEnums.length != 0) {
            boolean exists = locationsEnums[0] == locationsEnum;
            if (locationsEnums.length != 1 && !exists) {
                return !exists;
            } else {
                return exists;
            }
        } else {
            return false;
        }
    }
}
