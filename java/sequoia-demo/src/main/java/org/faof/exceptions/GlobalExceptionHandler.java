package org.faof.exceptions;

import org.faof.domain.BaseResponse;
import org.faof.domain.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public BaseResponse<?> bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("business error：{}, msg: {}.",e.getErrorCode(), e.getErrorMsg());
        return ResponseFactory.failed(e);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<?> exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("system internal error: {}.", e.getMessage());
        return ResponseFactory.failed(new BizException(ExceptionEnum.RUNTIME_ERROR));
    }
}
