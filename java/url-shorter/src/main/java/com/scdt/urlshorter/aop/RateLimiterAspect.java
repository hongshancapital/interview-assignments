package com.scdt.urlshorter.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.scdt.urlshorter.annotation.RateLimit;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 流量限制切面（用于对标注有限流注解的方法进行限流）
 *
 * @author mingo
 */
@Aspect
@Component
public class RateLimiterAspect
{
    @Resource
    private HttpServletResponse response;

    /**
     * 不同的接口，不同的流量控制
     * map的key为 RateLimit.key，value为RateLimit
     */
    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    @Around("@annotation(com.scdt.urlshorter.annotation.RateLimit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
        //取得有限流注解的方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RateLimit rateLimitAnnotation = method.getAnnotation(RateLimit.class);
        if (rateLimitAnnotation != null)
        {
            RateLimiter rateLimiter;
            // 取得限流方法的KEY
            String limiterKey = rateLimitAnnotation.key();
            if (!rateLimiterMap.containsKey(limiterKey))
            {
                //建立KEY与限流策略映射表
                rateLimiter = RateLimiter.create(rateLimitAnnotation.permitsPerSecond());
                rateLimiterMap.put(limiterKey, rateLimiter);
            }
            else
            {
                //命中则取得RateLimiter
                rateLimiter = rateLimiterMap.get(limiterKey);
            }

            //取得令牌（带超时时间）
            boolean acquire = rateLimiter.tryAcquire(rateLimitAnnotation.timeout(), rateLimitAnnotation.timeunit());
            if (!acquire)
            {
                //响应限流提示信息
                writeResponse(rateLimitAnnotation.msg());
                return null;
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 回写响应消息
     *
     * @param result 响应消息
     */
    public void writeResponse(String result)
    {
        response.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = response.getOutputStream())
        {
            outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
