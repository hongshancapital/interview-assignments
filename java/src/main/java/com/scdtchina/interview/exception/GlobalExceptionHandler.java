package com.scdtchina.interview.exception;

import com.scdtchina.interview.dto.rsp.BaseRsp;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseRsp<?> validationErrorHandler(MethodArgumentNotValidException ex) {

        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new BaseRsp<>(HttpStatus.BAD_REQUEST.value(), errorInformation.toString(), null);
    }

    @ExceptionHandler(BusinessException.class)
    public BaseRsp<?> validationErrorHandler(BusinessException ex) {
        return new BaseRsp<>(ex.getErrorCode(), ex.getErrorMsg(), null);
    }
}
