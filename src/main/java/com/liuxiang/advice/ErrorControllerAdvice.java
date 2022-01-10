package com.liuxiang.advice;

import com.liuxiang.model.view.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常统一处理
 * @author liuxiang6
 * @date 2022-01-06
 **/
@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public CommonResult handleException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        if (e instanceof RuntimeException) {
            response.setStatus(412);
        }

        log.warn("[" + request.getRequestURI() + "]Uncaught exception", e);
        return CommonResult.errorWithMsg(e.getMessage());
    }
}
