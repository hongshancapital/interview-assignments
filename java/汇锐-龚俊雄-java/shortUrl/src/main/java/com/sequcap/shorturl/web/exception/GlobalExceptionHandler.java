package com.sequcap.shorturl.web.exception;

import com.sequcap.shorturl.web.resp.ShortUrlResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * request uri is not support
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ShortUrlResp handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("The request URI'{}' with method '{}' is not support", requestURI, e.getMethod());
        return ShortUrlResp.error(e.getMessage());
    }

    /**
     * unknown system error
     */
    @ExceptionHandler(RuntimeException.class)
    public ShortUrlResp handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("The request URI'{}' happened unknown error", requestURI, e);
        return ShortUrlResp.error(e.getMessage());
    }

    /**
     * internal system error
     */
    @ExceptionHandler(Exception.class)
    public ShortUrlResp handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("The request URI'{}' happened internal system error.", requestURI, e);
        return ShortUrlResp.error(e.getMessage());
    }

 }
