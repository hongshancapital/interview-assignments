package com.hongshan.shorturl.domain.exception;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
public abstract class AbstractBizException extends RuntimeException {
    private int code;
    private String message;

    public AbstractBizException() {
        super();
    }

    public AbstractBizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
