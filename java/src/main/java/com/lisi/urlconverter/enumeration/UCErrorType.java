package com.lisi.urlconverter.enumeration;

/**
 * @description: 异常信息
 * @author: li si
 */
public enum  UCErrorType {
    COMMON_EXCEPTION("系统繁忙，请稍后再试", "WC00"),
    MEMORY_NOT_ENOUGH_EXCEPTION("存储空间不足", "WC01"),
    URL_NOT_FOUND_EXCEPTION("未查到域名，请确认域名是否过期", "WC02");

    UCErrorType(String errMsg, String errCode) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    // 错误码
    private String errCode;

    // 错误信息
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
