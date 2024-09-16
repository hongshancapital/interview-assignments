package com.tb.link.domain.model.enums;

/**
 *
 * @author andy.lhc
 * @date 2022/4/16 13:11
 */
public enum ErrorCodeEnum {

    PARAM_IS_INVALID("param.is.invalid","参数不合法"),

    SUPPORT_HTTP_OR_HTTPS("spport.http.or.https","仅支持http/https"),

    LONG_LINK_LENGTH_LESS_THAN_4096("long.link.length.less.than.4096","长链长度应小于4096"),

    SHORT_LINK_NOT_FOUND("short.link.not.found","短链不存在"),
    SHORT_LINK_HAS_EXPIRE("short.link.has.expire","短链已过期"),

    SHORT_LINK_IN_VALID("short.link.is.invalid","短链不合法"),

    SHORT_LINK_LENGTH_LESS_THAN_8("short.link.length.less.than.8","短链长度应小于8"),

    ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST("origin.short.link.is.not.in.whitelist","长链接域名不在白名单内"),

    SHORT_LINK_IS_NOT_IN_WHITELIST("short.link.is.not.in.whitelist","短链接域名不在白名单内"),

    APP_KEY_IS_NOT_IN_WHITELIST("app.key.is.not.in.whitelist","appKey不在白名单内"),

    ;

    private String errorCode ;

    private String errorMsg ;

    ErrorCodeEnum(String errorCode, String errorMsg){
        this.errorCode = errorCode ;
        this.errorMsg = errorMsg ;

    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
