package com.jingdata.exception;

import lombok.AllArgsConstructor;

/**
 * 异常码
 */
@AllArgsConstructor
public enum ExceptionCode {

    SUCCESS(200,"成功"),
    UNKNOWN_ERROR(1000,"未知错误"),
    PARAMS_ERROR(1001,"参数错误"),
    OVER_LIMIT_ERROR(1002,"超出个数限制"),
    UNSUPPORT(1003,"未知的存储类型"),
    URL_LENGTH_OVER(1004,"url长度溢出");
//    PARAMS_ERROR(1004,"未知错误"),
//    PARAMS_ERROR(1005,"未知错误");

    private Integer code;
    private String errorMessage;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
