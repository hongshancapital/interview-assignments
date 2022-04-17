package com.yilong.shorturl.common.enums;

public enum ErrorEnum {

    UNHANDLED_EXCEPTION(500,900500, "Unhandled exception"),
    INVALID_ARGUMENT(400,900400, "Invalid argument");

    private Integer httpStatus;
    private Integer errorCode;
    private String errorMsg;

    ErrorEnum(Integer httpStatus, Integer errorCode, String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getHttpStatus() {return httpStatus;}

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
