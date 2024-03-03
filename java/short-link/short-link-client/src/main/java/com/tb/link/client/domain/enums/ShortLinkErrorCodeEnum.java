package com.tb.link.client.domain.enums;

/**
 *
 * @author andy.lhc
 * @date 2022/4/16 13:11
 */
public enum ShortLinkErrorCodeEnum {

    SYSTEM_ERROR("system.error","系統异常"),
    PARAM_IS_NOT_NULL("param.is.notnull","参数不能为空"),
    PARAM_IS_INVALID("param.is.invalid","参数不合法"),


    ;

    private String errorCode ;

    private String errorMsg ;

    ShortLinkErrorCodeEnum(String errorCode,String errorMsg){
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
