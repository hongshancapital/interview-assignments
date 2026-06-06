package com.heyenan.shorturldemo.common.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * @author heyenan
 * @description 返回状态代码标识
 *
 * @date 2020/5/07
 */
@Getter
public enum Encode {
    SUCCESS(true, 200, "操作成功！"),
    // 不存在
    NOT_FOUND(false, 4010, "接口不存在"),
    // 异常
    LONG_URL_ERROR(false, 5677, "长链接格式错误"),
    PARAM_ERROR(false, 5010, "入参异常"),
    PARAM_FORMAT_ERROR(false, 5090, "入参格式异常"),
    PARAMETER_VALIDATION(false, 4030, "原始长链接不能为空"),
    ;

    /**
     * 操作是否成功
     */
    private final boolean success;
    /**
     * 操作代码
     */
    private final int code;
    /**
     * 提示信息
     */
    private final String message;
    /**
     * 异常时间
     */
    private final String date;

    Encode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now().toString();
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
