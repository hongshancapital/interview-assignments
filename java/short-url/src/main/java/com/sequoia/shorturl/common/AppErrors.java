package com.sequoia.shorturl.common;

/**
 *
 * 错误信息枚举
 *
 * @Author xiaojun
 *
 * @Date 2021/6/27
 *
 * @version v1.0.0
 *
 */
public enum AppErrors {

    private String errorCode;
    private String message;

    private AppErrors(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String errorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String message(String errorCode) {
        String message = null;
        for (AppErrors err : AppErrors.values()) {
            if (err.errorCode().equals(errorCode)) {
                message = err.message();
                break;
            }
        }
        return message;
    }
}
