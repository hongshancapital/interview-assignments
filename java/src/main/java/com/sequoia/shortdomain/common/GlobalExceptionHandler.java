package com.sequoia.shortdomain.common;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult customException(Exception e){

        ResponseResult result=new ResponseResult();
        result.setCode(ShortDomainConst.GET_Exception);
        result.setData(Strings.EMPTY);
        result.setMsg(ShortDomainConst.GET_Exception_MSG);

        return result;
    }

}
