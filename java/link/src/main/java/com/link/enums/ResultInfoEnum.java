package com.link.enums;

/**
 * @auth zong_hai@126.com
 * @date 2022-04-16
 * @desc
 */
public enum ResultInfoEnum {
    SUCCESS("0000", "成功"),
    SYS_EXCEPTION("0001", "系统异常"),
    SYS_PARAM_IS_NULL("0002", "参数不正确"),
    QPS_LIMIT("0003", "请求频繁，请稍后再试"),
    ;

    ResultInfoEnum(String resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * 响应码
     */
    private String resultCode;
    /**
     * 响应描述
     */
    private String resultDesc;
}
