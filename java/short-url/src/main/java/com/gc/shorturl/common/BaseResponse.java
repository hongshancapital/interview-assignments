package com.gc.shorturl.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Author: GC
 * @Date: 2021/3/4
 */
public class BaseResponse<T> {

    int code = 200;
    String message;
    T data;

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

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse response = new BaseResponse();
        response.setData(data);
        return response;
    }

    public static BaseResponse failed(int code, String message) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static BaseResponse failed(String message) {
        BaseResponse response = new BaseResponse();
        response.setCode(500);
        response.setMessage(message);
        return response;
    }
}
