package com.wangxingchao.shorturl.utils.enums;

/**
 * 针对
 */
public enum ResultErrorEnum {
    URL_PARAM_URL_ERROR(10001, "域名格式错误"),
    URL_MAX_LENGTH_ERROR(10002, "短域名长度超出预设大小");

    public final int code;

    public final String msg;

    ResultErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
