package com.scdt.shortlink.client.domain;

/**
 * @Author tzf
 * @Date 2022/4/30
 */
public enum ShortLinkErrorCode implements ErrorCode {
    /**
     * 短链模块错误码
     */
    ORIGINAL_LINK_EMPTY("100001", "长链不能为空"),
    ORIGINAL_LINK_RULE_ERROR("100002", "长链格式错误"),

    SHORT_LINK_EMPTY("100003", "短链不能为空"),
    SHORT_LINK_RULE_ERROR("100004", "短链格式错误"),

    ORIGINAL_LINK_NOT_EXIST("100005", "长链不存在"),

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
    ShortLinkErrorCode(String errorCode, String errorMessage) {
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
