package com.example.demo.common;

/**
 * @Author: dsm
 * @Description: 接口状态信息
 * @Date Create in 2021/12/23 10:25
 */
public enum ResultCode {
    SUCCESS(true, 200, "成功"),
    TOKEN_IS_BLANK(false, 405, "token为空");
    private boolean success;
    private Integer code;
    private String message;

    ResultCode( boolean success, Integer code, String message ) {
        this.success=success;
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
