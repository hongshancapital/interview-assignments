package com.scdt.tinyurl.exception;

import com.scdt.tinyurl.common.ErrorCode;

public class GlobalException extends RuntimeException {

    private String code;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
