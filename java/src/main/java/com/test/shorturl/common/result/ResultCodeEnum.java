package com.test.shorturl.common.result;
/**
 * @Author: liurenyuan
 * @Date: 2021/11/10
 * @Version: 1.0
 */
public enum ResultCodeEnum {

    UNKNOWN_ERROR("404", "未知错误"),
    EXCEEDS_MAXIMUM_VALUE("601", "序列号超过最大值"),
    EXCEEDS_ZERO_VALUE("605", "序列号不能小于0"),
    SHORT_URL_NOT_EXISTS("602", "短地址不存在"),
    ORIGINAL_NOT_EMPTY("603", "原始地址不能为空"),
    SHORT_NOT_EMPTY("604", "短地址不能为空"),
    SUCCESS("0","成功"),
    ERROR ("1","失败");

    private String code;

    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
