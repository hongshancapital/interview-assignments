package com.wg.common;

public enum ResultCodeEnum implements ResultCode {

    SUCCESS(20000, "成功"),
    LINK_INVALID(30001, "链接已失效"),
    PARAM_INVALID(30002, "无效的参数"),

    SERVICE_ERROR(99999, "服务器异常");
    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
