package com.example.demo.bean.result;

import java.io.Serializable;

/**
 * @author shenbing
 * @since 2022/1/8
 */
public class Result<T> implements Serializable {

    /**
     * 0-成功，非0-失败
     */
    private Integer status;

    private String msg;

    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

}
