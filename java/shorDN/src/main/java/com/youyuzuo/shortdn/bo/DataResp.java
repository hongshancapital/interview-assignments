package com.youyuzuo.shortdn.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataResp<T> {
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    private int code;
    private String msg;
    private T data;

    public DataResp(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static String systemError(){
        return new StringBuilder("{")
                .append('"').append("code").append('"').append(":").append(ERROR)
                .append(", ").append('"').append("msg").append('"').append(":").append('"').append("系统异常").append('"')
                .append("}").toString();
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
