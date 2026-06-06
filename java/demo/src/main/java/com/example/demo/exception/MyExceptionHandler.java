package com.example.demo.exception;

import com.example.demo.common.Result;
import com.example.demo.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: dsm
 * @Description: 全局异常处理
 * @Date Create in 2021/12/23 10:25
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result <String> handlerException( Exception exception ) {
        Result <String> result=new Result <String>();
        log.info(exception.getMessage());
        exception.printStackTrace();
        result.setResult(false, 500, "系统出错，请联系管理员");
        return result;
    }
}
