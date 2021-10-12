package com.moonciki.interview.commons.aop;

import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.model.ResponseContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    private void printRequestURI(){
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();

        String requestURI = request.getRequestURI();

        log.warn("requestURI=>{}", requestURI);

    }

    /**
     * 参数校验异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseContent handleBindException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();

        String fieldName = fieldError.getField();
        String errorMsg = fieldError.getDefaultMessage();
        printRequestURI();
        log.warn("Parameter Valid Error. [{}] : {}", fieldName, errorMsg);
        ResponseContent resp = ResponseContent.failResponse(ResponseEnum.request_error.info(errorMsg));
        return resp;
    }

    /**
     * 参数校验异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseContent handleBindException(BindException ex) {
        //校验 除了 requestbody 注解方式的参数校验 对应的 bindingresult 为 BeanPropertyBindingResult

        FieldError fieldError = ex.getBindingResult().getFieldError();

        String fieldName = fieldError.getField();
        String errorMsg = fieldError.getDefaultMessage();
        printRequestURI();
        log.warn("Parameter Valid Error. [{}] : {}", fieldName, errorMsg);

        ResponseContent resp = ResponseContent.failResponse(ResponseEnum.request_error.info(errorMsg));
        return resp;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseContent handleBindException(HttpMessageNotReadableException ex) {

        String errorMsg = ex.getMessage();
        printRequestURI();
        log.warn("Parameter Error. {}", errorMsg);

        ResponseContent resp = ResponseContent.failResponse(ResponseEnum.request_error.info(errorMsg));
        return resp;
    }

    /**
     * 统一处理所有拦截
     */
    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseContent handleNumberFormatException(NumberFormatException e) {
        String errorMsg = e.getMessage();
        printRequestURI();
        log.warn("Parameter Error!", e);

        ResponseContent resp = ResponseContent.failResponse(ResponseEnum.request_error.info(errorMsg));
        return resp;
    }

    /**
     * 统一处理所有拦截
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseContent handleTypeException(MethodArgumentTypeMismatchException e) {
        String errorMsg = e.getMessage();
        printRequestURI();
        log.warn("Parameter Error!", e);

        ResponseContent resp = ResponseContent.failResponse(ResponseEnum.request_error.info(errorMsg));
        return resp;
    }

    /**
     * 统一处理所有拦截
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseContent handleException(Exception e) {
        String errorMsg = e.getMessage();
        printRequestURI();
        log.warn("Parameter Error!", e);

        ResponseContent resp = ResponseContent.failResponse(e);
        return resp;
    }

}
