package com.sequoia.domain.entity;

/**
 * 统一返回数据结构
 *
 * @param <T>
 */
public class Response<T> {
    private T data;
    private String result;

    public Response() {
    }

    public Response(T data, String result) {
        this.data = data;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response<T> success(T data) {
        this.data = data;
        this.result = "ok";
        return this;
    }

    public Response<T> failed(T message) {
        this.data = message;
        this.result = "error";
        return this;
    }
}
