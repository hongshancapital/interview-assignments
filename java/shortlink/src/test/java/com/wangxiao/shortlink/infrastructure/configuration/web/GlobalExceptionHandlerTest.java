package com.wangxiao.shortlink.infrastructure.configuration.web;

import com.wangxiao.shortlink.infrastructure.common.ErrorEnum;
import com.wangxiao.shortlink.infrastructure.common.PersistenceException;
import com.wangxiao.shortlink.infrastructure.common.Result;
import com.wangxiao.shortlink.infrastructure.common.StoreOverFlowException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBadArgumentHandler() {
        Result result = globalExceptionHandler.badArgumentHandler(new StoreOverFlowException());
        Assertions.assertEquals(ErrorEnum.STORE_OVERFLOW.getCode(), result.getCode());
    }

    @Test
    void testBadArgumentHandler2() {
        Result result = globalExceptionHandler.badArgumentHandler(new PersistenceException());
        Assertions.assertEquals(ErrorEnum.PERSIST_ERROR.getCode(), result.getCode());
    }

    @Test
    void testSeriousHandler() {
        Result result = globalExceptionHandler.seriousHandler(new RuntimeException());
        Assertions.assertEquals(ErrorEnum.SYSTEM_ERROR.getCode(), result.getCode());
    }
}

