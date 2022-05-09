package com.wangxiao.shortlink.infrastructure.configuration.web;

import com.wangxiao.shortlink.infrastructure.common.ErrorEnum;
import com.wangxiao.shortlink.infrastructure.common.Result;
import com.wangxiao.shortlink.infrastructure.common.StoreOverFlowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(StoreOverFlowException.class)
    @ResponseBody
    public Object badArgumentHandler(StoreOverFlowException e) {
        return Result.fail(ErrorEnum.STORE_OVERFLOW);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object seriousHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(ErrorEnum.SYSTEM_ERROR, e.getMessage());
    }
}
