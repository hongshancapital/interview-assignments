package com.yofei.shortlink.common;

public enum ResponseCode {

    SUCCESS(200, "success"),
    ERROR(500,"error"),
    ;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
