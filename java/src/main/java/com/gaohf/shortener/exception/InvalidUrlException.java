package com.gaohf.shortener.exception;

public class InvalidUrlException  extends RuntimeException{
    public InvalidUrlException(String message) {
        super(message);
    }
}
