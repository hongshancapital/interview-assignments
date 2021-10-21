package com.assignment.common.exception;

import com.assignment.common.enums.ErrorCode;

/**
 * @author: shifeng
 * @description:异常处理类
 **/
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -1304314689318898755L;
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(formatMsg(errorCode), null, false, false);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        super(formatMsg(errorCode), cause);
        this.errorCode = errorCode;
    }

    private static String formatMsg(ErrorCode errorCode) {
        return String.format("%s-%s", errorCode.getCode(), errorCode.getMessage());
    }

    public String code() {
        return errorCode.getCode();
    }

    public String message() {
        return errorCode.getMessage();
    }
}
