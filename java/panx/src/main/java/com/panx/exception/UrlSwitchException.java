package com.panx.exception;

public class UrlSwitchException extends Exception{
    private String message;

    public UrlSwitchException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
