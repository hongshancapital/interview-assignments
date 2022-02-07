package com.xg.shorturl.common;


import lombok.Getter;

/**
 * 业务异常
 * @author xionggen
 */
@Getter
public class BizException extends RuntimeException {

    private int code;

    public BizException(String message, BaseErrorCode errorCode) {
        super(message);
        this.code = errorCode.getCode();
    }
}
