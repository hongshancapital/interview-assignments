package com.skg.domain.demo.anno;

/**
 * @Author smith skg
 * @Date 2021/10/13 17:15
 * @Version 1.0
 */
public enum ErrorCode {

    /**
     * 成功
     */
    OK(0, "success"),
    FAIL(1000, "fail"),
    ALERT(1001, "alert"),
    ALERT2(1002, "alert"),
    ERROR(500, "error"),

    /**
     * 请求错误
     */
    BAD_REQUEST(4000, "bad_request"),
    UNAUTHORIZED(4010, "unauthorized"),
    NOT_FOUND(4004, "not_found"),
    METHOD_NOT_ALLOWED(4005, "method_not_allowed"),

    DOMAIN_NOT_EXIST(5000, "短域名不存在"),
    ;

    private int code;
    private String message;

    ErrorCode() {
    }

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getResultEnum(int code) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return ERROR;
    }

    public static ErrorCode getResultEnum(String message) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        return ERROR;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
