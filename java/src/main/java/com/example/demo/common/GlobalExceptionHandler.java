package com.example.demo.common;

import com.example.demo.response.BaseResponse;
import com.example.demo.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<ResultCode> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        // 按需重新封装需要返回的错误信息
        List<String> invalidArguments = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            invalidArguments.add(error.getDefaultMessage());
            log.error(error.getDefaultMessage());
        }
        return BaseResponse.error(ResultCode.PARAM_ERROR, StringUtils.join(Constants.COMMA, invalidArguments));
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResponse<ResultCode> handlerException(Exception e) {
        log.error("全局异常", e);
        return BaseResponse.error(ResultCode.UNKNOWN_EXCEPTION);
    }
}
