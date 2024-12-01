package com.shorturl.enums;

public enum ResponseEnum {
    /**
     * API调用成功返回
     */
    SUCCESS(10000,"请求成功"),
    AUTH_ERROR(10001,"认证失败"),
    NO_DATA(10002,"短域对应的链接不存在"),
    FAIL(99999,"请求失败");
    

    private int errCode = 0;

    private String errMsg;


    private ResponseEnum(int errCode, String errMsg) {
       this.errCode = errCode;
       this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
