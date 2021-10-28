package com.sunjinghao.shorturl.common.exception;

import com.sunjinghao.shorturl.common.result.GlobalHttpResult;
import com.sunjinghao.shorturl.common.result.GlobalResultCodeEnum;
import com.sunjinghao.shorturl.common.util.GlobalHttpResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sunjinghao
 */
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public GlobalHttpResult methodArgumentNotValidException(Exception e) {

        //如果抛出的异常属于自定义异常，就以JSON格式返回
        if (e instanceof MethodArgumentNotValidException) {
            return GlobalHttpResultUtil.error(GlobalResultCodeEnum.INVALID_REQUEST.getCode(), ((MethodArgumentNotValidException) e).getFieldError().getDefaultMessage());
        }
        //如果都不是就打印出异常的信息
        return GlobalHttpResultUtil.error(GlobalResultCodeEnum.SERVER_ERROR.getCode(), GlobalResultCodeEnum.SERVER_ERROR.getMsg() + "错误的信息为：" + e.getMessage());
    }
}

