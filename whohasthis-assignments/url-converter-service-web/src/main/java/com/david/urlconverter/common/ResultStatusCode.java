package com.david.urlconverter.common;

/**
 * 结果状态码
 */
public enum ResultStatusCode {
    COMMON_SUCCESS("0","ok"),
    COMMON_FAILED("7000","failed"),
    EMPTY_PARAM("7001","请求参数为空"),
    ERROR_FORMAT_PARAM("7002","请求参数格式错误"),
    INVALID_SHORT_URL("7003","短域名无效");

    private final String code;

    private final String msg;

    ResultStatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
