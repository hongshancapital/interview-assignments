package com.web.work.common.exception;

/**
 * service error code
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
public enum ErrorCode {
    // service error
    URL_EXCEPTION("URL_EXCEPTION", "URL错误!"),
    EXCEPTION_ERROR("EXCEPTION_ERROR", "系统异常."),
    ;

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
