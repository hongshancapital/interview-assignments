package com.example.assignment.common.dto;

public enum ResponseEnum {

    SUCCESS("请求成功", 100000),
    ILLEGAL_URL("原始域名格式错误", 200001),
    ILLEGAL_SHORT_CODE("域名短码格式错误", 200002),
    SHORT_CODE_USE_OUT("域名短码已耗尽", 200003);

    private String message;
    private int code;

    ResponseEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }
}
