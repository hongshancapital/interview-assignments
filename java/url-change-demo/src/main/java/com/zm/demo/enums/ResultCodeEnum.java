package com.zm.demo.enums;

/**
 * @ClassName ResultCodeEnum
 * @Description 返回结果状态码
 * @Author zhaomin
 * @Date 2021/10/29 17:33
 **/
public enum ResultCodeEnum {

    SUCCESS(0, "执行成功"),
    PARAM_EMPTY(20002,"参数缺失"),
    UNKNOWN_ERROR(20001,"未知错误");

    int code;
    String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
