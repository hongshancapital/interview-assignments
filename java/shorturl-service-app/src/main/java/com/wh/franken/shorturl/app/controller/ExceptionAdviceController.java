package com.wh.franken.shorturl.app.controller;

import com.wh.franken.shorturl.app.exceptions.InvalidParameterException;
import com.wh.franken.shorturl.app.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fanliang
 */
@RestControllerAdvice
public class ExceptionAdviceController {

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
