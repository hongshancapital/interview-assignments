package com.hongshan.shorturl.model.exception;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
public class UrlExpireException extends AbstractBizException {
    private static int code = 417;

    public UrlExpireException() {
    }

    public UrlExpireException(String message) {
        super(code, message);
    }
}
