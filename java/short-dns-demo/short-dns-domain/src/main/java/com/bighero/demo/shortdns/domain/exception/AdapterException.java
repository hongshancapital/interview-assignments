package com.bighero.demo.shortdns.domain.exception;

/**
 * 基础设施层-适配器服务自定义异常
 * @author Administrator
 *
 */
public abstract class AdapterException extends RuntimeException {
    private String code;
    private String msg;

    public AdapterException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
