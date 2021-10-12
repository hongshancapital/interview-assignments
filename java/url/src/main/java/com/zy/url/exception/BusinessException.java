package com.zy.url.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private Integer errorCode;

    public BusinessException(String errorMsg) {
        this(ErrorEnum.BUSINESS_VALID.getCode(), errorMsg, null);
    }

    public BusinessException(Throwable cause) {
        this(ErrorEnum.BUSINESS_VALID.getCode(), cause.getMessage(), cause);
    }

    public BusinessException(String errorMsg, Throwable cause) {
        this(ErrorEnum.BUSINESS_VALID.getCode(), errorMsg, cause);
    }

    public BusinessException(Integer errorCode, String message) {
        this(errorCode, message, null);
    }

    public BusinessException() {
        this(ErrorEnum.BUSINESS_VALID.getMessage());
    }

    public BusinessException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
