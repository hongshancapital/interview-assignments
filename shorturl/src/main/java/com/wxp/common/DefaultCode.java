package com.wxp.common;

/**
 * @author owell
 * @date 2019/4/10 13:50
 */
public enum DefaultCode{

    SUCCESS(2000, "SUCCESS"),

    ILLEGAL_ARGUMENT(4000, "非法参数"),
    UNAUTHORIZED(4001, "无权限"),
    NO_DATA(4002, "无数据响应"),
    FORBIDDEN(4003, "禁止访问"),
    REQUEST_TIMEOUT(4008, "请求超时"),
    BUSINESS(4009, "请求超时"),

    SYS_ERROR(5000, "系统异常，请检查"),
    ;

    int code;
    String message;

    DefaultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
