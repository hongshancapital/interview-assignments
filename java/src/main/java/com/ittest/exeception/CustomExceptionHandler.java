package com.ittest.exeception;

import com.ittest.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/21 10:54
 * @Description: 公共异常简单处理 【实际真实业务，具体问题，具体处理】
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Object commonExceptionHandler(Exception ex) {
        return Result.error(500, ex.getMessage());
    }
}
