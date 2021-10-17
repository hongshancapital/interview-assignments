package com.zhangzheng.homework.exception;

import com.google.common.base.Throwables;
import com.zhangzheng.homework.controller.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param ex Exception异常
     * @return com.ziroom.zo.web.response.WebResponse
     * @methodName handleException
     * @description Exception异常处理
     * @author laigl
     * @Date 2019年09月25日 09:59:11
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResponse handleException(Exception ex) {
        log.error(Throwables.getStackTraceAsString(ex));
        return WebResponse.fail(ex.getMessage());
    }
}

