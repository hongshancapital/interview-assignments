package com.hongshan.shorturl.model.exception;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
public class NotAllowedException extends AbstractBizException {
    private static final int code = 403;
    private String message;

    public NotAllowedException() {
    }

    public NotAllowedException(String message) {
        super(code, message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
