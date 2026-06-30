package com.scdtchina.interview.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    SHORT_URL_INVALID(1001001, "short url invalid!");
    int code;
    String errorMsg;
    ErrorCodeEnum(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }
}
