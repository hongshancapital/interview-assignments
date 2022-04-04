package com.liupf.tiny.url.enums;

import lombok.Getter;

@Getter
public enum ApiCodeEnum {

    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求失败"),
    PARAM_ERROR(400, "参数校验错误"),
    SERVICE_BUSY(500, "服务器异常，请稍候重试"),

    /**
     * tiny-url 业务异常
     */
    URL_NOT_EXISTS(10001, "无效URL"),
    URL_TOO_LONG(10002, "URL太长，支持最大长度为2048"),
    URL_ERROR(10003, "URL格式错误"),
    ;

    private final Integer code;
    private final String msg;

    ApiCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
