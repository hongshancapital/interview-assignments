package com.wangxiao.shortlink.infrastructure.common;

public enum ErrorEnum {
    PARAMS_ILLEGAL(50001, "参数非法"),
    LINK_NOT_EXSITS(50002, "链接不存在"),
    STORE_OVERFLOW(50003, "存储超出限制！");
    private final Integer code;
    private final String reason;

    ErrorEnum(Integer code, String reason) {
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
