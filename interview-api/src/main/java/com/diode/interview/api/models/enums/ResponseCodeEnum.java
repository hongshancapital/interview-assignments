package com.diode.interview.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@AllArgsConstructor
public enum ResponseCodeEnum {
    SUCCESS(200, "成功"),
    ARGUMENT_ILLEGAL(400, "参数错误"),
    SYSTEM_ERROR(500, "系统错误"),
    ;

    @Getter
    final int code;
    @Getter
    final String desc;

}
