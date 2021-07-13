package com.scdt.domain.exception;

import com.scdt.domain.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public Result handleIllegalStateException(IllegalStateException exception){
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException exception){
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable throwable){
        return Result.fail("未知错误");
    }
}
