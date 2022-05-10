package com.scdt.web.handler;

import com.scdt.domin.BaseResult;
import com.scdt.domin.ErrorCode;
import com.scdt.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.validation.ValidationException;

/**
 * ApiExceprionHandler
 *
 * @author weixiao
 * @date 2022-04-26 11:45
 */
@Slf4j
@ControllerAdvice("com.scdt.web.api")
public class ApiExceprionHandler {

    /**
     * 请求错误
     */
    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class})
    public BaseResult errorHandler(ServletException ex) {
        return BaseResult.failure(ErrorCode.BAD_REQUEST);
    }

    /**
     * 参数校验失败
     */
    @ResponseBody
    @ExceptionHandler({ValidationException.class})
    public BaseResult errorHandler(ValidationException ex) {
        return BaseResult.failure(ErrorCode.BAD_REQUEST, ex.getMessage());
    }

    /**
     * 参数校验失败
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResult errorHandler(MethodArgumentNotValidException ex) {
        return BaseResult.failure(ErrorCode.BAD_REQUEST,
                ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 业务异常
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public BaseResult errorHandler(BusinessException ex) {
        return BaseResult.failure(ex.getError(), ex.getMsg());
    }

    /**
     * 未知异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResult errorHandler(Exception ex) {
        log.error("Uncaught exception", ex);
        return BaseResult.failure(ErrorCode.SERVER_ERROR);
    }
}
