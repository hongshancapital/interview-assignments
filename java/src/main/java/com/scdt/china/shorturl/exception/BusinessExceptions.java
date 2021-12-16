package com.scdt.china.shorturl.exception;

/**
 * 业务异常枚举
 *
 * @author ng-life
 */
public enum BusinessExceptions {

    /**
     * 无效URL
     */
    INVALID_URL(10001, "不是一个有效的URL"),

    /**
     * 不支持的URL协议
     */
    UNSUPPORTED_PROTOCOL(10002, "不支持的URL协议"),
    /**
     * 无效编码
     */
    INVALID_CODE(20001, "无效编码"),
    ;

    private final Integer errorCode;

    private final String errorMessage;

    BusinessExceptions(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    Integer getErrorCode() {
        return errorCode;
    }

    String getErrorMessage() {
        return errorMessage;
    }

}
