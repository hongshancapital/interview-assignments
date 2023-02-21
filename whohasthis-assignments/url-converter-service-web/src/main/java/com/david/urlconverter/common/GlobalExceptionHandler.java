package com.david.urlconverter.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     *
     */
    @ExceptionHandler(value = UrlConverterException.class)
    @ResponseBody
    public ResponseMessage bizExceptionHandler(UrlConverterException e) {
        log.error("自定义异常",e);
        return ResponseMessage.defineException(e);
    }

    /**
     * 处理其他异常
     *
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMessage exceptionHandler(Exception e) {
        log.error("系统执行异常",e);
        return ResponseMessage.otherException(e);
    }
}
