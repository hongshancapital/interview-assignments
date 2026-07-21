package com.scdt.urlshorter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author mingo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RateLimit
{
    /**
     * 唯一资源KEY，给每个接口设定不同的KEY
     */
    String key() default "";

    /**
     * 最多的访问次数
     */
    double permitsPerSecond();

    /**
     * 获取令牌的等待时间
     */
    long timeout();

    /**
     * 获取令牌最大等待时间,单位(例:分钟/秒/毫秒) 默认:毫秒
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 未取得令牌的提示语
     */
    String msg() default "系统繁忙, 请稍后再试.";
}
