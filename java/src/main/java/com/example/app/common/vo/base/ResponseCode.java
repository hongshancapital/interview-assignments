package com.example.app.common.vo.base;

/**
 * 响应状态码
 */
public enum ResponseCode {
    SUCCESS("200", "成功"),
    ERROR("500", "服务器内部未知错误"),
    ERROR_VALID("00001001", "业务异常"),
    PARAM_VALID("00001002", "参数异常");
    private String code;
    private String message;

    private ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}