package com.example.shortlink.infrastructure.common;

public class BizException extends RuntimeException {

    private final String code;
    private final String message;

    public BizException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BizException(String message, String code, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
