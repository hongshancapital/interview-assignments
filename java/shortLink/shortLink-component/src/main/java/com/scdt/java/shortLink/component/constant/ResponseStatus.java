package com.scdt.java.shortLink.component.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    SYSERROR(500, "系统异常，请稍后重试"),
    ;

    private final int status;
    private final String msg;
}
