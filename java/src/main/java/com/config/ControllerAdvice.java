package com.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * 提供标准的spring MVC异常处理
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Result<?>> exceptionHandler(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof ResultException) {
            log.warn("业务逻辑异常: {}", ((ResultException) ex).getResultStatus().getMessage());
            return this.handleResultException((ResultException) ex, headers, request);
        } else {
            log.error("系统异常"+ex.getMessage(),ex);
            return this.handleException(ex, headers, request);
        }
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Result<?>> illegalArgumentExceptionHandler(HttpServletRequest request, HttpServletRequest req, Exception e) {
        String message = org.apache.commons.lang.StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "系统繁忙，请稍后重试";
        Result<?> body = Result.failure(ResultStatus.FAILURE.getCode(), message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<?>> bindException(BindException e) {
        log.info("BindException", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "";
        Result<?> body = Result.failure(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        FieldError fieldErrors = e.getBindingResult().getFieldError();
        String message = fieldErrors.getField() + fieldErrors.getDefaultMessage();
        Result<?> body = Result.failure(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // <3> 处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().findFirst().map(c -> c.getMessage()).toString();
        Result<?> body = Result.failure(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // <4> 处理单个参数校验失败抛出的异常，自定义异常
    @ExceptionHandler(RequestParamException.class)
    public ResponseEntity<Result<?>> gioParamExceptionHandler(RequestParamException e) {
        String message = e.getMessage();
        Result<?> body = Result.failure(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * 对ResultException类返回返回结果的处理
     */
    protected ResponseEntity<Result<?>> handleResultException(ResultException ex, HttpHeaders headers, WebRequest request) {
        String message = (StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : ex.getResultStatus().getMessage());
        Result<?> body = Result.failure(ex.getResultStatus().getCode(), message);
        HttpStatus status = ex.getResultStatus().getHttpStatus();
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * 异常类的统一处理
     */
    protected ResponseEntity<Result<?>> handleException(Exception ex, HttpHeaders headers, WebRequest request) {
        Result<?> body = Result.failure();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    protected ResponseEntity<Result<?>> handleExceptionInternal(
            Exception ex, Result<?> body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
