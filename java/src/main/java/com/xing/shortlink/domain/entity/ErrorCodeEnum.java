package com.xing.shortlink.domain.entity;

import lombok.AllArgsConstructor;

/**
 * 异常错误定义
 *
 * @Author xingzhe
 * @Date 2021/7/17 22:52
 */
@AllArgsConstructor
public enum ErrorCodeEnum {
    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 参数校验异常
     */
    PARAMETER_VALIDATION_ERROR(1001, "参数校验错误"),

    /**
     * 短链接生成超过最大限制
     */
    SHORT_URL_OVER_LIMIT(1002, "短链接生成超过最大限制"),

    /**
     * 无效短链接
     */
    INVALID_SHORT_URL(1003, "无效短链接"),

    /**
     * 短链接生成失败
     */
    SHORT_URL_CREATE_ERROR(1004, "短链接生成失败"),

    /**
     * 系统内部异常
     */
    UNKNOWN_EXCEPTION(1005, "系统内部异常");

    /**
     * code, 错误码
     */
    private Integer code;

    /**
     * msg, 错误描述
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
