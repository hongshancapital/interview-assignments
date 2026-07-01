package com.redwood.shorturl.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description: 响应码枚举类
 * @Author: Jack-ZG
 * @Date: 2021-12-08 18:02
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseEnum {
    // 数据操作错误定义
    SUCCESS(200, "success"),
    BODY_NOT_MATCH(400, "Unknown error."),//msg 可以被自定义信息所替换
    SIGNATURE_NOT_MATCH(401, "The requested digital signatures do not match."),
    NOT_FOUND(404, "The resource was not found."),
    INTERNAL_SERVER_ERROR(500, "internal error."),
    SERVER_BUSY(503, "Server is busy, please try again later.");

    /**
     * 错误码
     */
    @Getter
    private int code;

    /**
     * 错误描述
     */
    @Getter
    private String msg;


}
