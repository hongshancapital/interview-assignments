package com.lynnhom.sctdurlshortservice.config.interceptor;

import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import com.lynnhom.sctdurlshortservice.config.filter.RequestWrapper;
import com.lynnhom.sctdurlshortservice.model.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @description: 通用异常处理
 * @author: Lynnhom
 * @create: 2021-05-07 21:00
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    Object handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        RequestWrapper requestWrapper = request instanceof RequestWrapper ? (RequestWrapper) request :
                new RequestWrapper(request);
        log.warn("uri:{}, method:{}, params:{}, body:{}, msg:{}", request.getRequestURI(),
                requestWrapper.getMethod(), requestWrapper.getQueryString(), requestWrapper.getBody(),
                e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringJoiner joiner = new StringJoiner("; ");
        constraintViolations.forEach(c -> joiner.add(c.getMessage()));
        return BaseResponse.fail("-1", joiner.toString());
    }

    @ExceptionHandler(value = { Exception.class })
    Object handleException(Exception e, HttpServletRequest request) {
        RequestWrapper requestWrapper = request instanceof RequestWrapper ? (RequestWrapper) request : new RequestWrapper(request);
        @SuppressWarnings("rawtypes")
        BaseResponse resObj;
        if (e instanceof BizException) {
            BizException newE = (BizException) e;
            resObj = BaseResponse.fail(newE.getBizCode(), newE.getBizMsg());
            log.warn("uri:{}, method:{}, params:{}, body:{}, msg:{}", request.getRequestURI(),
                    requestWrapper.getMethod(), requestWrapper.getQueryString(), requestWrapper.getBody(),
                    ((BizException) e).getBizCode(), e);
        } else if (e instanceof MultipartException){
            resObj = BaseResponse.fail("-1","文件大小超过限制");
            log.warn("uri:{}, method:{}, params:{}, body:{}, msg:{}", request.getRequestURI(),
                    requestWrapper.getMethod(), requestWrapper.getQueryString(), requestWrapper.getBody(),
                    e.getMessage(), e);
        } else {
            resObj = BaseResponse.fail("-1", e.getMessage());
            log.error("uri:{}, method:{}, params:{}, body:{}, msg:{}", request.getRequestURI(),
                    requestWrapper.getMethod(), requestWrapper.getQueryString(), requestWrapper.getBody(),
                    e.getMessage(), e);
        }
        return resObj;
    }
}
