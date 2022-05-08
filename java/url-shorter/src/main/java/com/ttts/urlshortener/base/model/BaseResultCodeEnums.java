package com.ttts.urlshortener.base.model;

/**
 * 返回码枚举
 */
public enum BaseResultCodeEnums {
    SUCCESS("0000", "成功"),
    URL_INVALID("4000", "短链接无效"),
    PARAMS_INVALID("4100", "参数无效"),
    URL_CREATE_FAIL("4200", "短链接生成失败"),
    ADD_SENDER_NUMS_FAIL("4300", "生成发号段失败"),

    LACK_CAPACITY("5000", "容量不足"),
    ;

    private String code;
    private String msg;

    BaseResultCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
