package com.sequoia.infrastructure.common;

import lombok.Getter;

/**
 * StatusCodeEnum
 *
 * @author KVLT
 * @date 2022-03-30.
 */
public enum StatusCodeEnum {

    /**
     * 常规状态码
     */
    OK(0, "ok"),

    BAD_REQUEST(4000, "请求失败"),
    PARAM_ERROR(4001, "请求参数不合法"),
    NOT_FOUND(4004, "请求地址不存在"),


    SERVE_ERROR(5000, "服务端内部异常"),
    SERVE_TIMEOUT(5001, "服务超时"),
    ;

    /**
     * 状态码
     */
    @Getter
    private int code;

    /**
     * 状态描述
     */
    @Getter
    private String msg;

    /**
     * 状态码枚举
     * @param code
     * @param msg
     */
    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
