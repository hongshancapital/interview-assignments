package com.shorturl.entity;

import com.shorturl.enums.ResponseEnum;

/**
 * @author shaochengming
 * @date 2021/10/15
 */
public class ResponseDto {

    private int errCode = 0;

    private String errMsg;

    private Object data;

    public ResponseDto(){

    }

    public ResponseDto(Object data) {
        this.data = data;
    }

    public ResponseDto(ResponseEnum responseEnum){
        this.errCode = responseEnum.getErrCode();
        this.errMsg = responseEnum.getErrMsg();
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
