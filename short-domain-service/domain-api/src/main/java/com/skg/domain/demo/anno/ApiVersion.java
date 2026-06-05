package com.skg.domain.demo.anno;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @auther skg
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    /**
     * 标识版本号
     * @return
     */
    String value() default "1.0";
}
