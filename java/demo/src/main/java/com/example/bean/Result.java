package com.example.bean;

import java.util.Map;

/**
 *   @author wenbin
 */
public class Result {
    private String code ="0";
    private String message="ok";

    private Map bean;

    public Map getBean() {
        return bean;
    }

    public void setBean(Map bean) {
        this.bean = bean;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
