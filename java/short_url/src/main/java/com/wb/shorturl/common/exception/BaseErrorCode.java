package com.wb.shorturl.common.exception;

import lombok.Getter;

/**
 * @author bing.wang
 * @date 2021/1/8
 */
@Getter
public enum BaseErrorCode implements ErrorCode {

    PARAMS_ERROR(400, "参数错误"),
    NOT_LOGGED(403, "未登录"),
    NOT_FOUND(404, "未找到资源"),
    SERVER_IS_BUSY(500, "系统繁忙, 请稍后再试"),
    ;

    BaseErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;
    private final String message;
}
