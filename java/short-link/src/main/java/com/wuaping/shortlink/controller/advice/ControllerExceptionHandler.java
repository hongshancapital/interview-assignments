package com.wuaping.shortlink.controller.advice;

import com.wuaping.shortlink.exception.ExceptionCodeMessage;
import com.wuaping.shortlink.exception.ServiceException;
import com.wuaping.shortlink.model.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 统一异常处理
 *
 * @author Aping
 * @since 2022/3/19 14:52
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResult<Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return RestResult.error(ExceptionCodeMessage.INVALID_PARAM.getCode(), ExceptionCodeMessage.INVALID_PARAM.getMessage());
    }

    @ExceptionHandler({ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class,
            HttpRequestMethodNotSupportedException.class})
    public RestResult<Object> servletRequestBindingException(Exception e) {
        return RestResult.error(ExceptionCodeMessage.INVALID_PARAM.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public RestResult<Object> bindException(BindException e) {
        StringBuilder message = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> message.append(",").append(fieldError.getField())
                .append("[").append(fieldError.getDefaultMessage()).append("]"));
        return RestResult.error(ExceptionCodeMessage.INVALID_PARAM.getCode(), message.substring(1));
    }

    @ExceptionHandler(ServiceException.class)
    public RestResult<Object> businessException(ServiceException e) {
        log.error("service exception", e);
        return RestResult.error(e.getErrCode(), e.getMessage());
    }
}