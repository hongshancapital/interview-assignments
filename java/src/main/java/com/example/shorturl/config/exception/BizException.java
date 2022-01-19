package com.example.shorturl.config.exception;

/**
 * @author yyp
 * @date 2022/1/16 10:37
 */
public class BizException extends RuntimeException {
    private String code;
    private String message;

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(String message) {
        this("", message);
    }
}
