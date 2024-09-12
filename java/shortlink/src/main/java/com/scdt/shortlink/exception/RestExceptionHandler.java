package com.scdt.shortlink.exception;

import com.scdt.shortlink.entity.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    public CommonResponse processException(Exception exception,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response
    ) {
        log.error(exception.getMessage());
        return CommonResponse.fail(ErrorEnum.EXCEPTION);
    }
}
