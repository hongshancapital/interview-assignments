package com.scdt.shortlink.exception;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    INVALID_URL("900001", "请输入正确的长链接"),
    INVALID_KEY("900002", "无效短域名"),
    EXCEPTION("999999", "服务器异常");

    private String code;
    private String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
