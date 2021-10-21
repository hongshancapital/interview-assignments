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
public @interface ApiLog {

}
