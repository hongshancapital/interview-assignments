package com.scdt.domin;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode
 *
 * @author weixiao
 * @date 2022-04-26 11:37
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    SERVER_ERROR(1, "服务器内部错误"),
    BAD_REQUEST(2, "请求参数错误"),
    NOT_FOUND(3, "请求资源不存在"),
    ;

    private final int code;
    private final String msg;
}
