package com.zz.exception;

import com.zz.exception.code.ErrorCode;

/**
 * 用于内部业务异常
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class BusinessException extends Exception {
    private String code;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable t) {
        super(message, t);
        this.code = errorCode.getCode();
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode, Throwable t) {
        super(errorCode.getMessage(), t);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
