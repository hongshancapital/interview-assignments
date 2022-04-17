package com.yilong.shorturl.bootstrap.advice;

import com.yilong.shorturl.common.enums.ErrorEnum;
import com.yilong.shorturl.common.exception.BusinessException;
import com.yilong.shorturl.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.util.List;

@ControllerAdvice({"com.yilong.shorturl"})
@Component
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, ValidationException.class})
    @ResponseBody
    public Result handleParameterVerificationException(Exception e) {
        log.error("Invalid arguments", e);
        String msg = null;
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (errors.isEmpty()) {
                msg = ErrorEnum.INVALID_ARGUMENT.getErrorMsg();
            } else {
                msg = (errors.get(0)).getDefaultMessage();
            }
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            String paramName = ((MethodArgumentTypeMismatchException) e).getName();
            msg = "Invalid " + paramName;
        } else {
            msg = "bad request params";
        }
        return Result.Builder.error(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), ErrorEnum.INVALID_ARGUMENT.getErrorCode(), msg);
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result handleBusinessException(BusinessException ex) {
        Result result = Result.Builder.error(ex.getCode(),ex.getMessage());
        return result;
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result handleGeneralException(Throwable throwable){
        return Result.Builder.error(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), throwable.getMessage());
    }
}
