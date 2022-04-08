package com.java.assignment.web.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class CurrentLimitInterceptor implements HandlerInterceptor {

    private final static String SEPARATOR = "-";

//    @Autowired
//    private RedisTemplate<String, Long> redisTemplate;

    //方便演示，没有用redis，用Map临时替代
    private Map<String, Long> redisTemplate = new HashMap<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //通过HandlerMethod获取方法CurrentLimit注解
            CurrentLimit currentLimit = handlerMethod.getMethodAnnotation(CurrentLimit.class);
            //如果此方法存在限流注解
            if (currentLimit != null) {
                int number = currentLimit.count();
                long time = currentLimit.timespan();
                //如果次数和时间限制都大于0证明此处需要限流
                if (time > 0 && number > 0) {
                    //这里的可以定义的是项目路径+API路径+ip，当然我这里就没去获取实际的ip了。key可以根据你们项目实际场景去设定
                    String key = request.getContextPath() + SEPARATOR + request.getServletPath() + SEPARATOR + "ip";
                    //获取reids缓存中的访问次数
//                    Long numberRedis = redisTemplate.opsForValue().get(key);
                    Long numberRedis = redisTemplate.get(key);
                    //如果是第一次访问，则设置此ip访问此API次数为1，并设置失效时间为注解中的时间
                    if (null == numberRedis) {
//                        redisTemplate.opsForValue().set(key, 1L, time, TimeUnit.SECONDS);
                        redisTemplate.put(key, 1L);
                        return true;
                    }
                    //如果访问次数大于注解设定则抛出异常
                    if (numberRedis >= number) {
                        throw new RuntimeException("请求频繁，请稍后重试！");
                    }
                    //如果满足限流条件则更新缓存次数
                    redisTemplate.put(key, numberRedis + 1);
                }
            }
        }
        return true;
    }
}

