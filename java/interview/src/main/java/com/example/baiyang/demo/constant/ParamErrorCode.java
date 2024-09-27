package com.example.baiyang.demo.constant;

import lombok.Getter;

public enum ParamErrorCode implements ErrorCode {

    ILLEGAL_PARAM_ERROR("2000", "参数错误"),

    URL_NOT_EXIST_ERROR("2001", "域名信息不存在");

    ParamErrorCode(String code, String message) {
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
