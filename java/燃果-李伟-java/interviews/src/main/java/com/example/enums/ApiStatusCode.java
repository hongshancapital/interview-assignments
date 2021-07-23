package com.example.enums;

/**
 * 描述:
 * API执行状态编码枚举
 *
 * @author eric
 * @create 2021-07-21 5:08 下午
 */
public enum ApiStatusCode {
    SUCCESS("2000","操作成功"),

    NO_ACCESS("4000","无访问权限"),

    BUSINESS_EXCEPTION("5000","业务异常"),

    PARAM_INVALID("5001","参数缺失或无效"),

    NOT_LOGIN_IN("5002","未登录"),

    SYSTEM_EXCEPTION("6000","系统异常"),

    FAILED("9000","操作失败");

    private final String code; //状态码
    private final String description; //描述

    private ApiStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
