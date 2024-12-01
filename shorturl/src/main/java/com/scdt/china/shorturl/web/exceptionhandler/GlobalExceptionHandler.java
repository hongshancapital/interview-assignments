package com.scdt.china.shorturl.web.exceptionhandler;

import com.scdt.china.shorturl.service.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error(ex.getMessage(), ex);

        String errorMsg = null;

        if (ex instanceof BizException) {
            errorMsg = ex.getMessage();
        } else {
            errorMsg = "System error! Message is " + ex.getMessage();
        }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        try {
            response.getWriter().write(errorMsg);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("error to write errorMsg", ex);
        }

        return new ModelAndView();
    }
}
