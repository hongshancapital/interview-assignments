package com.sequoia.shorturl.common.exception.handler;

import com.sequoia.shorturl.common.ApiResult;
import com.sequoia.shorturl.common.exception.ObjectNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;


    @Test
    public void test(){
        ApiResult<?> failure = globalExceptionHandler.handleException(new ObjectNotExistException("failure"));
        assertEquals("failure", failure.getMsg());
        ApiResult<?> result = globalExceptionHandler.handleException(new ObjectNotExistException(""));
        assertEquals("服务器异常", result.getMsg());
    }
}