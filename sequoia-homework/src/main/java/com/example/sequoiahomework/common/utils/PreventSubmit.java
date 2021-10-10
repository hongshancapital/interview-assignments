package com.example.sequoiahomework.common.utils;

import java.lang.annotation.*;

/**
 * @author Irvin
 * @description 防重复提交的注解
 * @date 2021/10/10
 */
//注解放置的目标位置,METHOD是可注解在方法级别上
@Target(ElementType.METHOD)
//注解在哪个阶段执行
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreventSubmit {

    /**
     * 锁过期时间 毫秒
     */
    int time() default 500;

}
