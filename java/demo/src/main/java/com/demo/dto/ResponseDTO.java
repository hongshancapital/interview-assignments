package com.demo.dto;


import java.io.Serializable;

/**
 * Created  by syd on 2022/1/12 0008.
 */
public class ResponseDTO<T> implements Serializable {
    private static final Integer SUCCESS = 0;
    /**
     * 响应code
     */
    private Integer code;
    /**
     * 响应message
     */
    private String message;
    /**
     * 响应对象
     */
    private T data;

    public ResponseDTO() {
        this(SUCCESS, "OK");
    }

    public ResponseDTO(int code, String message) {
        this.code(code).message(message);
    }

    public boolean isSuccess() {
        return SUCCESS.equals(this.code);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public ResponseDTO<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseDTO<T> code(int code) {
        this.setCode(code);
        return this;
    }

    public ResponseDTO<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public static <T> ResponseDTO<T> ok() {
        return new ResponseDTO();
    }

    public static <T> ResponseDTO<T> error(String message) {
        ResponseDTO<T> resp = new ResponseDTO();
        resp.setCode(-1);
        resp.setMessage(message);
        return resp;
    }

    public static <T> ResponseDTO<T> error(int code, String message) {
        ResponseDTO<T> resp = new ResponseDTO();
        resp.setCode(code);
        resp.setMessage(message);
        return resp;
    }
}
