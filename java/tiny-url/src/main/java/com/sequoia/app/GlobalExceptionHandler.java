package com.sequoia.app;

import com.sequoia.infrastructure.common.ApiResult;
import com.sequoia.infrastructure.common.exception.LockException;
import com.sequoia.infrastructure.common.exception.TinyCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Descript:
 * File: com.sequoia.app.GlobalExceptionHandler
 * Author: daishengkai
 * Date: 2022/3/30 19:59
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockException.class)
    public ApiResult<?> handleLockException(LockException exception) {
        log.error("加锁异常", exception);
        return ApiResult.error("服务器内部异常");
    }

    @ExceptionHandler(TinyCodeException.class)
    public ApiResult<?> handleTinyCodeException(TinyCodeException exception) {
        log.error("生成短链接异常", exception);
        return ApiResult.error("服务器内部异常");
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleUnkonwnException(Exception exception) {
        log.error("发生未知错误", exception);
        return ApiResult.error("服务器内部异常[未知错误]");
    }

}
