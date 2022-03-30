package com.hongshan.shorturl.model.exception;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
public class NotFoundException extends AbstractBizException{
    private static int code = 404;

    public NotFoundException(String message) {
        super(code, message);
    }

    public NotFoundException() {
        super();
    }
}
