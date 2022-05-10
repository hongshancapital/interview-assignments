package com.lenfen.short_domain.exception;

/**
 * 系统异常
 */
public class ShortDomainException extends RuntimeException {

    public ShortDomainException() {
        super();
    }

    public ShortDomainException(String message) {
        super(message);
    }
}
