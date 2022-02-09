package com.scdt.assignment.infrastructure.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 用于统一处理在Endpoint中由于异常而带回客户端的错误信息
 **/
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e){
        log.error("发生了一个异常", e);
        return e.getMessage();
    }
}