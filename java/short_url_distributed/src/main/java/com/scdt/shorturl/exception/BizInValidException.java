package com.scdt.shorturl.exception;

/**
 *  题目要求不准用Validation的包
 *  用此类代替 MethodArgumentNotValidException
 */
public class BizInValidException extends RuntimeException{

    public BizInValidException(ExceptionMessage message,Object... args) {
        super(String.format(message.getMsg(),args));
    }
}
