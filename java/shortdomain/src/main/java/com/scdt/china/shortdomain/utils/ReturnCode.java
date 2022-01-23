package com.scdt.china.shortdomain.utils;

/**
 * @Author: CZ
 * @Date: 2022/1/22 13:04
 * @Description:
 */

public enum ReturnCode {
    SUCCESS("0000", "请求成功"),
    PARAM_ERROR("0002", "参数异常"),
    OUT_OF_RANGE_ERROR("0003", "短域名已用尽"),
    NOT_EXISTS_ERROR("0004", "该短域名不存在对应长域名"),
    FAILED("9999", "请求失败");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
