package com.sequoiacap.shortlinkcenter.common.exception;

import com.sequoiacap.shortlinkcenter.common.enums.IErrorCode;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public enum CommonErrorCodeEnum implements IErrorCode {

    EXECUTOR_ERROR(10000002, "运行错误"),
    ;

    private int code;

    private String message;

    CommonErrorCodeEnum(int code, String message) {
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
