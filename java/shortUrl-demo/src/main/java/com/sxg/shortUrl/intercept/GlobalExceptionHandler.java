package com.sxg.shortUrl.intercept;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sxg.shortUrl.utils.Result;

import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author sxg
 *
 */
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> globalExceptionHandler(HttpServletResponse response, RuntimeException e) {
        response.setStatus(500);
        e.printStackTrace();
        return Result.error(e);
    }
}
