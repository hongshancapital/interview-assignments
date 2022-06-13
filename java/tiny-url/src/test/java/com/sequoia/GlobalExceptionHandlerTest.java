package com.sequoia;

import com.sequoia.app.GlobalExceptionHandler;
import com.sequoia.infrastructure.common.ApiResult;
import com.sequoia.infrastructure.common.StatusCodeEnum;
import com.sequoia.infrastructure.common.exception.LockException;
import com.sequoia.infrastructure.common.exception.TinyCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Descript:
 * File: com.sequoia.GlobalExceptionHandlerTest
 * Author: daishengkai
 * Date: 2022/3/31
 * Copyright (c) 2022,All Rights Reserved.
 */
@SpringBootTest
public class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler handler;

    @Test
    public void testHandleLockException() {
        ApiResult result = handler.handleLockException(new LockException());
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        result = handler.handleLockException(new LockException("锁异常"));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        result = handler.handleLockException(new LockException("锁异常", new RuntimeException()));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());
    }

    @Test
    public void testHandleTinyCodeException() {
        ApiResult result = handler.handleTinyCodeException(new TinyCodeException());
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        result = handler.handleTinyCodeException(new TinyCodeException("生成短链接异常"));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        result = handler.handleTinyCodeException(new TinyCodeException("生成短链接异常", new RuntimeException()));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());
    }

    @Test
    public void testHandleUnkonwnException() {
        ApiResult result = handler.handleUnkonwnException(new InterruptedException("中断异常"));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        result = handler.handleUnkonwnException(new RuntimeException("未知异常"));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());
    }

}
