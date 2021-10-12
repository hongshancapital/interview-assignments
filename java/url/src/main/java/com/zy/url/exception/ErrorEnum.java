package com.zy.url.exception;

import lombok.Getter;

/**
 * 通用错误
 */
@Getter
public enum ErrorEnum {
    BUSINESS_VALID(-103, "业务异常")
    ,DATA_VALID(-102, "参数不合法")
    ,USER_AUTHORIZE_ERROR(-104, "用户未授权")
    ,MAX_UPLOAD_SIZE(-105, "文件大小超出")
    ,USER_VERIFY_ERROR(-101, "用户未登录")
    ,SYSTEM_ERROR(-100,"服务器未知错误")
    ;
    private Integer code;
    private String message;
    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
