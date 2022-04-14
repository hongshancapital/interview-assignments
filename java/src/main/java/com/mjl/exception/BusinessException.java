package com.mjl.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String errorCode) {
        super(errorCode);
    }
}