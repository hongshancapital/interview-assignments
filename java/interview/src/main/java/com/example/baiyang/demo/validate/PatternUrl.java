package com.example.baiyang.demo.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/17
 * @description: 自定义校验url的注解
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PatternUrlValidator.class})
public @interface PatternUrl {
    String regexp() default "";

    String message() default "url格式不符合规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
