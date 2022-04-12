package com.eagle.shorturl.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author eagle
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ResultCodeEnum {

    SUCCESS(2000, "操作成功"),
    ERROR_AUTH(4001, "权限不足"),
    ERROR_PARAM(4002, "参数校验失败"),
    ERROR_BLOOM_FILTER(4003, "操作布隆过滤器失败"),
    ERROR_JSON(4004, "操作JSON失败"),
    ERROR(5000, "操作失败"),
    ;

    private int code;
    private String message;

}
