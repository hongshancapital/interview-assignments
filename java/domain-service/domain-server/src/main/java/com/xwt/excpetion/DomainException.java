package com.xwt.excpetion;

import lombok.Getter;

/**
 * 域名服务异常
 *
 * @author xiwentao
 */
public class DomainException extends RuntimeException {

    @Getter
    private String code;

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, String code) {
        super(message);
        this.code = code;
    }
}
