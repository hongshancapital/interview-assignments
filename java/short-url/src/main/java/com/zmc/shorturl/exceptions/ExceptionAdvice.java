package com.zmc.shorturl.exceptions;

import com.zmc.shorturl.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description: 异常捕获
 * Author: Zmc
 * Date: 2021-12-11 18:20
 **/
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleInvalidParam(InvalidParameterException e) {
        return Result.invalidResult(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleInternalError(Exception e) {
        return Result.internalErrorResult(e.getMessage());
    }
}
