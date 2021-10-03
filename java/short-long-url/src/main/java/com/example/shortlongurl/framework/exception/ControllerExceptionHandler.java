package com.example.shortlongurl.framework.exception;

import com.example.shortlongurl.framework.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public R customerException(CustomException e) {
        log.error("用户自定义异常",e);
        return R.fail(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e) {
        //TODO 将异常信息持久化处理，方便运维人员处理
        log.error("系统异常",e);
        //没有被程序员发现，并转换为CustomException的异常，都是其他异常或者未知异常.
        return R.fail(e.getMessage());
    }
}
