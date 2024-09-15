package com.icbc.gjljfl.common;

import java.io.Serializable;

public class ResponseEntity implements Serializable {
    private static final long serialVersionUID = 1001L;
    private String code;
    private String msg;
    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResponseEntity(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity(String code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public ResponseEntity() {
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
