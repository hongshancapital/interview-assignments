package com.scdt.tinyurl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiter {

    //资源名称
    String name() default "";

    // 限制每秒访问次数，默认最大即不限制
    double accessRate() default Double.MAX_VALUE;
}
