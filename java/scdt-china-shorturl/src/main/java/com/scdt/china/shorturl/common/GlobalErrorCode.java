package com.scdt.china.shorturl.common;

/**
 * @Author zhouchao
 * @Date 2021/11/26 14:51
 * @Description 全局错误码
 **/
public enum GlobalErrorCode {
    /**
     * 返回码
     */
    SUCCESS("0", "OK"),
    NO_SHORT_URL("30001", "未查询到相关的短链接"),
    MISS_PARAMETER("30002", "缺少必要的参数"),

    SYSTEM_ERROR("40000", "系统错误"),
    SHORTURL_CREATE_FAIL("40001", "短链接创建失败"),
    PARAMETER_ERROR("40002", "参数异常"),
    ;


    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
