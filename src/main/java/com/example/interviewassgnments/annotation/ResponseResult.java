/**
 * this is a test project
 */

package com.example.interviewassgnments.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:39
 * @Description: com.example.interviewassgnments.annotation
 * @version: 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
