package com.zz.ratelimit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 *
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiterAnno {
    int NOT_LIMITED = 0;

    /**
     * 限流注解key
     */
    String key();

    /**
     * 每秒请求限制
     */
    int QPS() default NOT_LIMITED;

    /**
     * 超时时间，默认不等待
     */
    int timeout() default 0;

    /**
     * 超时时间单位，默认是毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MICROSECONDS;
}
