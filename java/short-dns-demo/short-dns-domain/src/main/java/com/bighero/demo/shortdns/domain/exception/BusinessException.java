package com.bighero.demo.shortdns.domain.exception;

/**
 * 领域服务自定义异常
 * @author Administrator
 *
 */

public class BusinessException extends RuntimeException {
    private String code;
    private String msg;

    public BusinessException(String code,String msg){
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
