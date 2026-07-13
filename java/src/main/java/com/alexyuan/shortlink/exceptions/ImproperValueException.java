package com.alexyuan.shortlink.exceptions;

public class ImproperValueException extends RuntimeException{

    public ImproperValueException() {
        super();
    }

    public ImproperValueException(String s) {
        super(s);
    }
}
