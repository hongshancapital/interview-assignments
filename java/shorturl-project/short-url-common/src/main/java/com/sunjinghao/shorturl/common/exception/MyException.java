package com.sunjinghao.shorturl.common.exception;

/**
 * @author sunjinghao
 */
public class MyException extends RuntimeException{
    private String msg;

    public MyException(String msg) {
        super(msg);
    }
}

