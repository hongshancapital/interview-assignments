package com.example.demo.core.web;

import java.io.Serializable;

public class MessageCode implements Serializable {

    private Integer code;
    private String msg;

    public MessageCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static MessageCode of(Integer code, String msg) {
        return new MessageCode(code, msg);
    }
}
