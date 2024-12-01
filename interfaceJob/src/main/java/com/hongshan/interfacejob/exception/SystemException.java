package com.hongshan.interfacejob.exception;

import lombok.Data;

@Data
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**错误编码*/
    private int code;

    /**内容*/
    private String msg;

    public SystemException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}