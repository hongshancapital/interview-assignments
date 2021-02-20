package com.example.surl.intercept;

import com.example.surl.utils.Ajax;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


/**
 * @author 杨欢
 */
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Ajax<Object> globalExceptionHandler(HttpServletResponse response, RuntimeException e) {
        response.setStatus(500);
        e.printStackTrace();
        return Ajax.error(e);
    }
}
