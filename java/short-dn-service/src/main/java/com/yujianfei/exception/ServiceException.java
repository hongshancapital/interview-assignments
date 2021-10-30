package com.yujianfei.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -6031851860505599161L;

    private final String code;
    private final String msg;

    public ServiceException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
