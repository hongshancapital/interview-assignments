package com.scdt.china.shorturl.exception;

/**
 * 业务异常
 *
 * @author ng-life
 */
public class BusinessException extends RuntimeException {

    /**
     * 业务异常码
     */
    private final Integer errorCode;

    public BusinessException(BusinessExceptions exception) {
        this(exception, exception.getErrorMessage());
    }

    public BusinessException(BusinessExceptions exception, String message) {
        super(message, null, false, false);
        this.errorCode = exception.getErrorCode();
    }

    /**
     * 获取业务异常码
     */
    public Integer getErrorCode() {
        return errorCode;
    }

}
