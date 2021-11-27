package com.domain.url.web;

import com.domain.url.exception.ServiceException;
import com.domain.url.web.data.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一异常处理
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult<?> handleServiceException(HttpServletRequest request, Throwable th) {
        ServiceException e = (ServiceException) th;
        log.error("Controller ServiceException-Path: [{}]", request.getRequestURI(), th);
        return JsonResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult<?> handleException(HttpServletRequest request, Throwable th) {
        log.error("Controller Exception-Path: [{}]", request.getRequestURI(), th);
        return JsonResult.fail("fail", "系统内部错误");
    }
}
