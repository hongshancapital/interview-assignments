package com.assignment.common.enums;

/**
 * 错误码枚举类
 *
 * @author shifeng
 */
public enum ErrorCode {
    SUCCESS("000000", "success"),
    REQ_PARAM_EMPTY("100000", "请求参数为空"),
    SHORT_URL_INVALID("200000", "找不到链接、链接已失效"),
    CREATE_SHORT_URL_ERROR("300000","生成短链接异常"),
    SYSTEM_ERROR("400000","服务异常"),

    ;


    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
