package com.example.assignment.controller.handler;

import com.example.assignment.Exception.ShortCodeUseOutException;
import com.example.assignment.common.dto.ResponseEnum;
import com.example.assignment.common.dto.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortCodeUseOutException.class)
    @ResponseBody
    public Result shortCodeUseOutExceptionHandler(Exception e) {
        Result<String> result = new Result<>();
        return result.failed(ResponseEnum.SHORT_CODE_USE_OUT);
    }
}
