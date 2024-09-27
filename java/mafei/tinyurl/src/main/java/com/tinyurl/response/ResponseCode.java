package com.tinyurl.response;

public enum ResponseCode {

    CODE_SUCCESS(1000, "成功"),
    CODE_SERVER_ERROR(10001, "系统正忙"),
    CODE_INPUT_NULL(10002, "输入参数为空");

    private Integer value;
    private String message;

    ResponseCode(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
