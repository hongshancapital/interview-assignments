package com.interview.log;

import java.lang.annotation.*;

/**
 * @Description :
 * @Author: nyacc
 * @Date: 2019-12-23 17:59
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WriteLog {

    LogLevel logLevel() default LogLevel.Nothing;

    enum LogLevel{
        /**
         * 仅记录时长
         */
        Nothing,
        /**
         * 记录参数
         */
        Params,
        /**
         * 记录参数和结果
         */
        Result
    }
}
