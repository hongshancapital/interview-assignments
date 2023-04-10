package com.example.baiyang.demo.constant;

import lombok.Getter;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description: 系统异常错误码
 */
public enum SystemErrorCode implements ErrorCode {

    SYSTEM_ERROR("8000", "系统错误");

    SystemErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Getter
    private String code;

    @Getter
    private String message;
}
