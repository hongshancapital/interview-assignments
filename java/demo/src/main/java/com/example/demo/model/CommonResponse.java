package com.example.demo.model;

/**
 * 通用响应类
 * @Author chenlong
 * @param <T>
 */
public class CommonResponse<T> {
    public static int SUCCESS;
    public static int ERROR;
    public static String SUCCESS_MSG;
    public static String ERROR_MSG;
    protected int code;
    protected String message;
    protected T data;

    public CommonResponse() {
    }

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse(SUCCESS, message, data);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse(SUCCESS, SUCCESS_MSG, (Object)null);
    }

    public static <T> CommonResponse<T> error(int code, String message, T data) {
        return new CommonResponse(code, message, data);
    }

    public static <T> CommonResponse<T> error(int code, String message) {
        return new CommonResponse(code, message, (Object)null);
    }

    public static <T> CommonResponse<T> error(String message, T data) {
        return new CommonResponse(ERROR, message, data);
    }

    public static <T> CommonResponse<T> error(String message) {
        return new CommonResponse(ERROR, message, (Object)null);
    }

    public static <T> CommonResponse<T> error() {
        return new CommonResponse(ERROR, ERROR_MSG, (Object)null);
    }

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    // 初始化响应编码，消息
    static {
        SUCCESS = 0;
        ERROR = -1;
        SUCCESS_MSG = "success";
        ERROR_MSG = "system error";
    }
}
