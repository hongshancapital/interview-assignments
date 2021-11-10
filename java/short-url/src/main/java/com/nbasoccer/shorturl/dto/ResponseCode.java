package com.nbasoccer.shorturl.dto;

public enum ResponseCode {

    SUCCESS(0, "请求成功"), INVALID_URL(-10, "无效网址"), PARAM_IS_NULL(-11, "参数无效或为空"), SERVER_ERROR(-99, "服务器异常");

    private String message;

    private int code;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}
