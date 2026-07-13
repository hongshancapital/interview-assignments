package com.example.sequoiahomework.config;/*
package com.example.sequoiahomework.config;


import com.alibaba.fastjson.JSON;
import com.example.sequoiahomework.common.response.DataResult;
import com.example.sequoiahomework.common.utils.PreventSubmit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

*/
/**
 /**
 * @author Irvin
 * @description 切面
 * @date 2021/10/9 18:11
 *//*

@Aspect
@Component
@Slf4j
public class AopAspect {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.example.sequoiahomework.common.utils.PreventSubmit)")
    public void preventSubmitCut() {
    }

    */
/**
     * 防重
     *
     * @return obj
     * @author irvin
     * @date 2020/8/12
     *//*

    @Around(value = "preventSubmitCut()")
    public Object preventSubmitAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //获取操作
        PreventSubmit submit = method.getAnnotation(PreventSubmit.class);
        //获取请求的所有属性
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        assert request != null;
        //获取目标方法的参数
        String requestParams = getRequestParams(joinPoint, request);
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = className + "." + method.getName();
        //参数加密
        String md5Str = DigestUtils.md5Hex(methodName + requestParams);
        Boolean hasKey = redisTemplate.hasKey(md5Str);
        if (null != hasKey && hasKey) {
            return new DataResult<>(0, 310, Boolean.FALSE, "请求过于频繁，稍后重试");
        } else {
            redisTemplate.opsForValue().set(md5Str, md5Str, submit.time(), TimeUnit.MILLISECONDS);
            return joinPoint.proceed();
        }
    }

    private String getRequestParams(JoinPoint joinPoint, HttpServletRequest request) {
        String requestParams;
        if ("Get".equalsIgnoreCase(request.getMethod())) {
            Map<String, String> rtnMap = convertMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            requestParams = JSON.toJSONString(rtnMap);
        } else {
            Object[] args = joinPoint.getArgs();
            requestParams = Arrays.toString(args);
        }
        return requestParams;
    }

    */
/**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author Irvin
     * @date 2020/10/10
     *//*

    public Map<String, String> convertMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>(16);
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }
}
*/
