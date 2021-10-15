package com.wangxingchao.shorturl.exception;

/**
 * 超出最大长度异常
 */
public class MaxLengthException extends RuntimeException{


    public MaxLengthException() {
    }

    public MaxLengthException(String message) {
        super(message);
    }
}
