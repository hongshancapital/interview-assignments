package com.domaincore.constants;

/**
 * 错误枚举类
 *
 * @author 905393944@qq.com
 * @Date 2021/9/21
 */
public enum ErrorInfoEnum {
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!"),
    SHORT_DOMAIN_ERROR("1001", "获取短域名异常"),
    LONG_DOMAIN_ERROR("1001", "获取长域名异常"),
    URL_EMPTY_ERROR("1002", "URL不能为空"),
    SHORTURL_LENGTH_ERROR("1003", "短域名长度过长");

    /**
     * 错误码
     */
    private String resultCode;

    /**
     * 错误描述
     */
    private String resultMsg;

    ErrorInfoEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
