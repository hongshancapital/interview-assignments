package org.domain.exception;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String s) {
        super(s);
    }
}
