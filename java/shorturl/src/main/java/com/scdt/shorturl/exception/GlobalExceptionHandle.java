package com.scdt.shorturl.exception;

import com.scdt.shorturl.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author leo
 * @Description: 全局异常处理器
 * @date 2022/2/26 10:22
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(ServerException.class)
    public Result<Object> serverExceptionHandler(ServerException ex) {
        log.error(ex.getMessage(),ex.getCode());
        return Result.fail(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getLocalizedMessage());
        return Result.fail("系统异常，请联系管理员!");
    }
}
