package com.francis.urltransfer.exception;

import com.francis.urltransfer.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Francis
 * @Description: 全局异常处理器
 * @date 2022/1/27 16:22
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(ServerException.class)
    public Result<Object> serverExceptionHandler(ServerException ex) {
        log.error(ex.getMessage());
        return Result.of(ex);
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getLocalizedMessage());
        return Result.fail(500, "系统异常，请联系管理员!");
    }
}
