package com.java.assignment.web.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentLimit {

    /**
     * 请求次数
     *
     * @return
     */
    int count();

    /**
     * 时间间隔
     *
     * @return
     */
    long timespan();
}

