package com.yofei.shortlink.dto;


public class PlainResponse  extends BaseResponse {

    public static PlainResponse success() {
        PlainResponse plain = new PlainResponse();
        plain.success = true;
        return plain;
    }

    public static PlainResponse failure(String errorMsg) {
        PlainResponse plain = new PlainResponse();
        plain.success = false;
        plain.errorMsg = errorMsg;
        return plain;
    }
}