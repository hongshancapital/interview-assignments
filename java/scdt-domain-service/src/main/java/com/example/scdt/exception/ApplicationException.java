package com.example.scdt.exception;

/**
 * 自定义应用异常
 */
public class ApplicationException extends Exception {

    // 异常信息
    private final String message;

    // 异常错误码
    private final int code;


    public ApplicationException(String message, int code) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
