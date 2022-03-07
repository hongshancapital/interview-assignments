package com.scdt.shortenurl.common.enums;

/**
 * @Description 返回结果码枚举类
 * @Author chenlipeng
 * @Date 2022/3/7 2:14 下午
 */
public enum ErrorCodeEnum {

    SUCCESS("00000", "请求成功"),
    PARAM_IS_NULL("10101", "参数为空"),
    ILLEGAL_PARAM("10102", "非法参数"),
    INTERNAL_SYSTEM_ERROR("50000", "系统内部异常"),
    ;

    private String code;

    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
