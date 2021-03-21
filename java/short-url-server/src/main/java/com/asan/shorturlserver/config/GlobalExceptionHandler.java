package com.asan.shorturlserver.config;

import com.asan.shorturlserver.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult handleException(Exception exception) {
        log.error("GlobalExceptionHandler:{}", exception.getMessage(), exception);
        return ResponseResult.fail("error");
    }
}