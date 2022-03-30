package com.hongshan.shorturl.model.exception;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
public class ServerErrorException extends AbstractBizException {
    private static int code = 500;

    public ServerErrorException(String message) {
        super(code, message);
    }

    public ServerErrorException() {
        super();
    }
}
