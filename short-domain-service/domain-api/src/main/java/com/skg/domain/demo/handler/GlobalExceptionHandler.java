package com.skg.domain.demo.handler;

import com.skg.domain.demo.anno.ErrorCode;
import com.skg.domain.demo.base.Result;
import com.skg.domain.demo.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @Author smith skg
 * @Date 2021/10/13 17:12
 * @Version 1.0
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static Result<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ErrorCode code = ErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        Result<Object> msg = Result.fail(code.getCode(),ex.getMessage());

        if (CollectionUtils.isNotEmpty(allErrors)) {
            for (ObjectError objectError : allErrors) {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    msg.put(objectError.getObjectName() + "." + fieldError.getField(), objectError.getDefaultMessage());
                } else {
                    msg.put(objectError.getObjectName(), objectError.getDefaultMessage());
                }
            }
        }
        return msg;

    }
    @ExceptionHandler({DomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static Result<Object> DomainExceptionException(DomainException ex) {
        Result<Object> msg = Result.fail(ErrorCode.DOMAIN_NOT_EXIST.getCode(),ex.getMessage());
        return msg;
    }

}
