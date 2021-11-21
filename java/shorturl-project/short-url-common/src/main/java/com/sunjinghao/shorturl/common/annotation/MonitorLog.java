package com.sunjinghao.shorturl.common.annotation;

import java.lang.annotation.*;

/**
 *注解方式
 *
 * @author sunjinghao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MonitorLog {

    String value() default "";
}
