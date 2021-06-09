package cn.office.luxixi.urlservice.config;

import cn.office.luxixi.urlservice.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Result<String> constraintViolationException(ConstraintViolationException constraintViolationException) {
        return Result.error(constraintViolationException.getMessage(), null);
    }
}
