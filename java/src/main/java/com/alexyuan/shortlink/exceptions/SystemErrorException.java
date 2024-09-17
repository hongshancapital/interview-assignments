package com.alexyuan.shortlink.exceptions;

public class SystemErrorException extends RuntimeException{

    public SystemErrorException() {
        super();
    }

    public SystemErrorException(String s) {
        super(s);
    }
}
