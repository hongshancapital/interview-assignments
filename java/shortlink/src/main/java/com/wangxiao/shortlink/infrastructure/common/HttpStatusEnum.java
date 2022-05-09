package com.wangxiao.shortlink.infrastructure.common;

public enum HttpStatusEnum {
    ;
    private final Integer code;
    private final String reason;

    HttpStatusEnum(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public Integer getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
