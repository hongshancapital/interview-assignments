package com.mjl.exception.handler;

import com.mjl.exception.BusinessException;
import com.mjl.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {"com.mjl.controller"})
public class ControllerExceptionHandler {

    final Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public  Object throwableHandler(HttpServletRequest request, BusinessException e) {
        logger.error("business exception", e);
        return Response.failed(e.getMessage());
    }
}
