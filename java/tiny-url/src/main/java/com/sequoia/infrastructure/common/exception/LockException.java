package com.sequoia.infrastructure.common.exception;

/**
 * Descript:
 * File: com.sequoia.infrastructure.common.exception.LockException
 * Author: daishengkai
 * Date: 2022/3/30 22:14
 * Copyright (c) 2022,All Rights Reserved.
 */
public class LockException extends RuntimeException {

    public LockException() {}

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable e) {
        super(message, e);
    }

}
