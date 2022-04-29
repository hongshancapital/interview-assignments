package com.sequoiacap.shortlinkcenter.domain.exception;

import com.sequoiacap.shortlinkcenter.common.enums.IErrorCode;

/**
 * @author h.cn
 * @date 2022/3/17
 */
public enum DomainErrorCodeEnum implements IErrorCode {

    PARAM_ERROR(20000001, "参数错误"),

    OBJECT_NOT_EXIST(20000001, "对象不存在"),

    EXTENSION_EXECUTOR_ERROR(20000002, "扩展点错误"),

    NO_PERMISSION(20000003, "权限错误"),

    VALIDATE_ERROR(20000004, "校验错误"),

    PROTOCOL_NOT_EXIST(20000005, "协议不存在"),

    RETRY_FAILED(20000006, "重试失败"),

    SAVE_FAILED(20000007, "保存失败"),
    ;

    private int code;

    private String message;

    DomainErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
