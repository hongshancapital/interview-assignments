package com.skylu.constants;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName ErrorCode.java
 * @Description TODO
 * @createTime 2022年04月22日 15:29:00
 */
public enum ErrorCode {
    REQUEST_SUCCESS(200, "访问成功"),
    BIZ_ERROR(500, "服务器异常");

    private final Integer code;

    private final String  message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
