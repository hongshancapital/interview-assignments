package com.scdt.interview.url.exception;

import com.scdt.interview.url.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author: lijin
 * @date: 2021年10月10日
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public R onBizException(BindException exception){
        log.warn(exception.getLocalizedMessage());
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        return R.fail(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    @ExceptionHandler({BizException.class,
            ConstraintViolationException.class,
            ServletRequestBindingException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R onBizException(Exception exception){
        log.warn(exception.getLocalizedMessage());
        return R.fail(exception.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> onException(Exception exception){
        log.error(exception.getLocalizedMessage(), exception);
        return R.fail(exception.getLocalizedMessage());
    }
}
