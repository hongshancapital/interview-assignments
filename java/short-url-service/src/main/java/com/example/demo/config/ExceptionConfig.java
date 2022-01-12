package com.example.demo.config;


import com.example.demo.model.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangxiaosong
 * @since 2022/1/10
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
    @ResponseBody
    public CommonResult<String> allException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return CommonResult.fail();
    }

}