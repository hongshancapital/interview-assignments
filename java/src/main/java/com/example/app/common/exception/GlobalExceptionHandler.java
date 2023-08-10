package com.example.app.common.exception;

import com.example.app.common.vo.base.ResponseCode;
import com.example.app.common.vo.base.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局国际化异常处理
 *
 * @author voidm
 * @date 2021/9/18
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 其他异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<Object> exceptionHandler(Exception exception, HttpServletRequest request) {
        if (exception instanceof ModuleException) {
            // 自定义业务异常处理
            ModuleException e = (ModuleException) exception;
            log.error("module error message:{}", e.getMessage());
            return ResponseResult.build(ResponseCode.ERROR_VALID, e.getMessage());
        } else {
            // 通用异常处理
            log.error("un known exception", exception);
            return ResponseResult.build(ResponseCode.ERROR, exception.getMessage());
        }
    }

    /**
     * 参数类异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult<Object> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        // 按需重新封装需要返回的错误信息
        List<String> paramValidationResults = new ArrayList<>();
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            paramValidationResults.add(error.getDefaultMessage());
        }
        return ResponseResult.build(ResponseCode.PARAM_VALID, paramValidationResults);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseResult<Object> constraintViolationException(BindException exception) {
        List<String> paramValidationResults = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            paramValidationResults.add(error.getDefaultMessage());
        }
        return ResponseResult.build(ResponseCode.PARAM_VALID, paramValidationResults);
    }
}