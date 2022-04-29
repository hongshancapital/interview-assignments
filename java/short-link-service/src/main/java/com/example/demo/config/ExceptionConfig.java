package com.example.demo.config;

import com.example.demo.bean.result.Result;
import com.example.demo.bean.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@ControllerAdvice
@ResponseBody
public class ExceptionConfig {

    private static final Logger log = LoggerFactory.getLogger(ExceptionConfig.class);

    /**
     * 统一异常拦截
     *
     * @param ex 异常对象
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<String> allException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Results.fail();
    }

}
