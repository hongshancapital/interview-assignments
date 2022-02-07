package com.xg.shorturl.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常code
 * @author xionggen
 */
@Getter
@AllArgsConstructor
public enum  BaseErrorCode {
    SUCCESS(0, "SUCCESS"),

    /* 预期异常 */
    ERROR_PARAMETER(1, "参数异常"),

    /* 非预期异常 */
    ERROR_SYSTEM_ERROR(999, "系统异常"),
    ;

    private int code;
    private String desc;

}
