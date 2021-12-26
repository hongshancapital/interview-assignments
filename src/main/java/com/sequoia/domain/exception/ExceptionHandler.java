package com.sequoia.domain.exception;

import com.sequoia.domain.entity.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionHandler {
    /**
     * 处理Controller抛出的未知异常，返回500httpcode
     * @param e 异常实例
     * @return Controller层的返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> exceptionHandler(Exception e){
        e.printStackTrace();
        return new Response<String>().failed("The system is busy, please try again later");
    }

    /**
     * 处理IllegalArgumentException异常, 返回400httpcode
     *
     * @param e 异常实例
     * @return Controller层的返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<String> exceptionHandler(IllegalArgumentException e){
        return new Response<String>().failed(e.getMessage());
    }

    /**
     * 处理UrlNotExistException异常, 返回400httpcode
     *
     * @param e 异常实例
     * @return Controller层的返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UrlNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<String> exceptionHandler(UrlNotExistException e){
        return new Response<String>().failed(e.getMessage());
    }
}