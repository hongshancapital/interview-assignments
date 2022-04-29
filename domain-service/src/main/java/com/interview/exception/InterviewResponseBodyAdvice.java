package com.interview.exception;


import com.interview.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: nyacc
 * @Date: 2021/12/17 13:40
 */

@RestControllerAdvice
@Slf4j
public class InterviewResponseBodyAdvice {

    public InterviewResponseBodyAdvice() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response uniteExceptionHandler(Exception e) {
        log.error("系统异常", e);
        return Response.buildFail(e.getMessage());
    }

    @ExceptionHandler({InterviewException.class})
    public Response businessExceptionHandler(InterviewException e) {
        log.error(e.getCode(), e.getMessage());
        log.error("系统异常", e);
        return Response.buildFail(e.getCode(), e.getMessage());
    }

}
