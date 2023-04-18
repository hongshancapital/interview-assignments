package com.hongshan.shorturl.domain.exception;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
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
