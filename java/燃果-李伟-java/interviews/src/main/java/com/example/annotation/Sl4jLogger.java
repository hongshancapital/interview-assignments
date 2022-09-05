package com.example.annotation;

import com.example.enums.LocationsEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Sl4jLogger {
    String prefixFormat() default "开始${shortClassName}.${methodName}入参:{}";

    String suffixFormat() default "调用${shortClassName}.${methodName}返回结果:{}";

    LocationsEnum[] locations() default {LocationsEnum.BEFORE, LocationsEnum.AFTER};
}
