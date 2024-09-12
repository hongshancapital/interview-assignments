package com.shortlink.common;

public class BusinessException extends RuntimeException{
    private String alertMsg;

    public BusinessException(String alertMsg){
        super(alertMsg);
        this.alertMsg = alertMsg;
    }

    public BusinessException(String alertMsg, Throwable e){
        super(alertMsg,e);
        this.alertMsg = alertMsg;
    }
}
