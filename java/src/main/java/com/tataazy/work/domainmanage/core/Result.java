package com.tataazy.work.domainmanage.core;

public class Result<T> implements java.io.Serializable {
    private boolean success = true;
    private String  errMsg;
    private T       data;

    public Result() {
        this.success = true;
        this.errMsg = "";
        this.data = null;
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(Boolean success, Integer code, String errMsg, String msg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
