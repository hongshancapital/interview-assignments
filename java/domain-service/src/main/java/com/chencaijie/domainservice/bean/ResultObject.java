package com.chencaijie.domainservice.bean;

public class ResultObject {
    public static final String SUCCESS = "0";
    public static final String FALSE = "1";

    private String code;
    private String data;
    private String errorMessage;

    public ResultObject(String code,String data,String errorMessage)
    {
        this.code=code;
        this.data=data;
        this.errorMessage=errorMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
