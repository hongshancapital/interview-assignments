package com.bighero.demo.shortdns.url.app.exception;

/**
 * 应用服务自定义异常
 * @author Administrator
 *
 */

public class ApplicationException extends RuntimeException {
    private String code;
    private String msg;

    public ApplicationException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
 
}
