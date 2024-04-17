package com.sequoia.infrastructure.common.exception;

/**
 * Descript:
 * File: com.sequoia.infrastructure.common.exception.TinyCodeException
 * Author: daishengkai
 * Date: 2022/3/30 22:26
 * Copyright (c) 2022,All Rights Reserved.
 */
public class TinyCodeException extends RuntimeException {

    public TinyCodeException() {}

    public TinyCodeException(String message) {
        super(message);
    }

    public TinyCodeException(String message, Throwable e) {
        super(message, e);
    }

}
