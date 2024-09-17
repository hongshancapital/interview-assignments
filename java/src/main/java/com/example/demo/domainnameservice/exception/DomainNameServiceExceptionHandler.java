package com.example.demo.domainnameservice.exception;

import com.example.demo.domainnameservice.constant.ErrorCode;
import com.example.demo.domainnameservice.entity.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * global exception handler.
 *
 * @author laurent
 * @date 2021-12-11 下午4:03
 */
@ControllerAdvice
public class DomainNameServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainNameServiceExceptionHandler.class.getSimpleName());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult<Object> handle(final Exception exception) {
        LOGGER.error("[global handle]", exception);
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(ErrorCode.UNKNOWN_ERROR);
        result.setMsg(exception.getMessage());
        if (exception instanceof DomainNameServiceException) {
            result.setCode(((DomainNameServiceException) exception).getCode());
        }
        return result;
    }
}
