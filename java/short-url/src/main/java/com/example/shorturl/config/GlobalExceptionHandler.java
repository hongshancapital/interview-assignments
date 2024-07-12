package com.example.shorturl.config;


import com.example.shorturl.vo.BusinessException;
import com.example.shorturl.vo.ResultCode;
import com.example.shorturl.vo.ResultVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.example.shorturl")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof String) {
            try {
                return objectMapper.writeValueAsString(ResultVo.success(o));
            } catch (JsonProcessingException e) {
                return ResultVo.failure(ResultCode.RESPONSE_WRAPPER_ERROR, e.getMessage());
            }
        }
        if (o instanceof ResultVo) {
            return o;
        }
        return ResultVo.success(o);
    }

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return this.handleExceptionInternal(ex, (Object) null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        // 使用统一返回格式，回填body
        if (body == null) {
            body = ResultVo.failure(status, getExceptionMsg(ex));
            log.error(body.toString(), ex);
        }
        return new ResponseEntity(body, headers, status);
    }

    /**
     * 获取简短、具体的异常信息
     *
     * @param ex
     * @return
     */
    protected String getExceptionMsg(Exception ex) {
        String msg = null;
        final String delimiter = ", ";
        if (ex instanceof BindException) {
            BindException e = (BindException) ex;
            msg = e.getAllErrors().stream().map
                    (ObjectError::getDefaultMessage).collect(Collectors.joining(delimiter));
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException e = (ConstraintViolationException) ex;
            msg = e.getConstraintViolations().stream().map
                    (ConstraintViolation::getMessage).collect(Collectors.joining(delimiter));
        } else {
            msg = ex.getMessage();
        }
        return msg;
    }
}
