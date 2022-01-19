package com.example.shorturl.config;

import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author yyp
 * @date 2022/1/16 16:29
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseResult bizExceptionHandler(BizException ex) {
        log.error("bizException", ex);
        return ResponseResult.fail(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult otherExceptionHandler(Exception ex) {
        log.error("exception", ex);
        return ResponseResult.fail("系统异常");
    }
}
