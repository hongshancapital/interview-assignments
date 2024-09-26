package com.scdt.shortlink.client.domain;

/**
 * @Author tzf
 * @Date 2022/4/30
 */
public enum BaseErrorCode implements ErrorCode {
    /**
     * 系统错误码
     */
    SYSTEM_ERROR("999999", "系统错误"),

    ;

    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 构造方法
     *
     * @param errorCode
     * @param errorMessage
     */
    BaseErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 获取错误码
     *
     * @return
     */
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    @Override
    public String getErrorMessgae() {
        return errorMessage;
    }

}
