package com.zp.response;

public class ResultData<T> {
    private int code;

    private String message;

    private T data;

    public static <T> ResultData sccuess(T data) {
        ResultData resultData = new ResultData();
        return resultData.setCode(200).setData(data);
    }

    public static <T> ResultData error(String message) {
        ResultData resultData = new ResultData();
        return resultData.setCode(500).setMessage(message);
    }

    public int getCode() {
        return code;
    }

    public ResultData setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultData setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultData setData(T data) {
        this.data = data;
        return this;
    }
}
