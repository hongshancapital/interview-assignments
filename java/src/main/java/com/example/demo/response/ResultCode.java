package com.example.demo.response;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum ResultCode {

    SUCCESS(true, 200, "操作成功！"),

    NOT_FOUND(false, 4010, "接口不存在"),
    PARAM_ERROR(false, 5010, "入参异常"),
    PARAM_FORMAT_ERROR(false, 5090, "入参格式异常"),
    DECRYPT_ERROR(false, 5610, "入参解密异常"),
    UNKNOWN_EXCEPTION(false, 5999, "未知异常"),

    PARAMETER_VALIDATION(false, 4030, "入参验证失败-{0}"),
    SHORTURL_LIMIT(false, 5100, "短连接不能超过8个字符"),
    SHORTURL_NOT_FOUND(false, 5200, "短连接不存在")

    ;

    /** 操作是否成功 */
    private final boolean success;
    /** 操作代码 */
    private final int code;
    /** 提示信息 */
    private final String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
