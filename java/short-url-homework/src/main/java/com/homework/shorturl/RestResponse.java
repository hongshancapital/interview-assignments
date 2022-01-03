package com.homework.shorturl;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResponse<T> implements Serializable {
    private String resultCode;
    private String resultMsg;
    private T data;

    public RestResponse() {
        this((T) null);
    }

    public RestResponse(T data) {
        this.resultCode = "0";
        this.resultMsg = "success";
        this.data = data;
    }

    public RestResponse(String resultCode, String resutMessage) {
        this.resultCode = resultCode;
        this.resultMsg = resutMessage;
    }

    public RestResponse(String resultCode, String resutMsg, T data) {
        this.resultCode = resultCode;
        this.resultMsg = resutMsg;
        this.data = data;
    }
}
