package com.julyday.shorturl.vo;

public class Result<T> {

    public static final int SUCCESS = 200;

    public static final int INVALID_PARAMETER = 400;

    public static final int INTERNAL_ERROR = 500;

    private int code;

    private String message;

    private T data;

    public static <D> Result<D> newResult(D data) {
        return new Result<>(SUCCESS, "success", data);
    }


    public static Result<?> invalidResult(String message) {
        Result<?> result = new Result<>();
        result.setCodeAndMessage(INVALID_PARAMETER, message);
        return result;
    }

    public static Result<?> internalErrorResult(String msg) {
        Result<?> result = new Result<>();
        result.setCodeAndMessage(INTERNAL_ERROR, msg);
        return result;
    }

    public void setCodeAndMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
