package com.cx.shorturl.app.vo;

public class Result<T> {

    public static final int SUCCESS = 0;

    public static final int INVALID_PARAMETER = 1;

    public static final int INTERNAL_ERROR = 2;

    private int errorCode;

    private String message;

    private T data;

    public static <D> Result<D> newResult(D data) {
        return new Result<>(SUCCESS, "success", data);
    }


    public static Result<?> invalidResult(String msg) {
        Result<?> result = new Result<>();
        result.setErrorCodeAndMessage(INVALID_PARAMETER, msg);
        return result;
    }

    public static Result<?> internalErrorResult(String msg) {
        Result<?> result = new Result<>();
        result.setErrorCodeAndMessage(INTERNAL_ERROR, msg);
        return result;
    }

    public void setErrorCodeAndMessage(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Result() {
    }

    public Result(int errorCode, String message, T data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
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
