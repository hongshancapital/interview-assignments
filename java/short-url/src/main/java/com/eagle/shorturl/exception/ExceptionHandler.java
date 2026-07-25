package com.eagle.shorturl.exception;

import com.eagle.shorturl.result.Result;
import com.eagle.shorturl.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author eagle
 * @description
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected Result<Void> handleException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
        } else if (ex instanceof MissingServletRequestParameterException) {
            return handleParameterException((MissingServletRequestParameterException) ex);
        }else {
            return handleOtherException(ex);
        }
    }

    private Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = transferBindingResult(ex.getBindingResult());
        return new Result<Void>().fail(ResultCodeEnum.ERROR_PARAM, message);
    }

    private Result<Void> handleParameterException(MissingServletRequestParameterException ex) {
        return new Result<Void>().fail(ResultCodeEnum.ERROR_PARAM, ex.getMessage());
    }

    private Result<Void> handleOtherException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new Result<Void>().fail(ResultCodeEnum.ERROR, ex.getMessage());
    }

    private String transferBindingResult(BindingResult result) {
        if (Objects.isNull(result) || CollectionUtils.isEmpty(result.getAllErrors())) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        result.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            sb.append(fieldName).append(errorMessage).append(". ");
        });

        return sb.toString();
    }
}
