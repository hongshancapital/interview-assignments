package com.github.shwas1.shorturl.exceptions;

import com.github.shwas1.shorturl.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleMyException(HttpServletRequest httpServletRequest, Exception e) {
        String servletPath = httpServletRequest.getServletPath();
        LOGGER.error("请求 [{}] 发生错误：", servletPath, e);
        return Result.fail(e);
    }
}
