package com.controller;

import com.beans.ResultObj;
import com.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalException {

    private final static Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultObj exceptionHandler(Exception e){
        printExceptionMsg(e, Constant.RESULT_OBJ_EXCEPTION_MSG) ;
        return new ResultObj(Constant.RESULT_OBJ_STATUS_FAIL, Constant.RESULT_OBJ_EXCEPTION_MSG);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultObj nullPorintExceptionHandler(NullPointerException e){
        printExceptionMsg(e, Constant.RESULT_OBJ_NULLPORINTEXCEPTION_MSG) ;
        return new ResultObj(Constant.RESULT_OBJ_STATUS_FAIL, Constant.RESULT_OBJ_NULLPORINTEXCEPTION_MSG);
    }

    private void printExceptionMsg(Exception e,String msg){
        StackTraceElement[] st = e.getStackTrace();
        Stream<StackTraceElement> array = Arrays.stream(st) ;
        Optional<StackTraceElement> first = array.findFirst();
        logger.info("异常原因-->{}-->{}-->{}",first.get().getClassName(),first.get().getMethodName(),first.get().getLineNumber());
        e.printStackTrace();
    }
}
