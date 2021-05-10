package com.zs.shorturl.exception;

import com.zs.shorturl.enity.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception 全局处理
 *
 * @author zs
 * @date 2021/5/10
 */
public class DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result exception(Exception e) {
        return Result.fail();
    }
}
