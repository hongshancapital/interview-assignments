package com.cn.exception;

/**
 * @author wukui
 * @date 2021--12--29
 **/
public class DomainConversionException extends RuntimeException {
    public String ErrCode;
    public String ErrMsg;

    public DomainConversionException(String ErrCode, String ErrMsg) {
        super(ErrMsg);
        this.ErrCode = ErrCode;
        this.ErrMsg = ErrMsg;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }
}
