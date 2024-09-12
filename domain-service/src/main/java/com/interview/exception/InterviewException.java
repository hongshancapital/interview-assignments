package com.interview.exception;

/**
 * @Author: nyacc
 * @Date: 2021/12/17 13:55
 */

public class InterviewException extends RuntimeException{

    private String code;

    public InterviewException(String code) {
        this.code = code;
    }

    public InterviewException(String code, Object message) {
        super(message.toString());
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
