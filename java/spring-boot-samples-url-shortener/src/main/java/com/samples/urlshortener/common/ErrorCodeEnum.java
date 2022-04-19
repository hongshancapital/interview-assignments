package com.samples.urlshortener.common;

/**
 * @author liuqu
 * @date 2022/4/17
 */
public enum ErrorCodeEnum {
    SUCCESS(1, "成功"),
    FAIL(0, "处理失败，请稍后重试或直接联系管理员"),
    PARAM_ERROR(900, "参数错误"),
    ;



    private int code;
    private String value;

    ErrorCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
