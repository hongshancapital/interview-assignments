package com.sequoia.domain.exception;

public class UrlNotExistException extends RuntimeException {

    public UrlNotExistException(String message) {
        super(message);
    }
}
