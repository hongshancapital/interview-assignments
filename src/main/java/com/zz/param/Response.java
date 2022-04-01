package com.zz.param;

/**
 * 外部请求的返回数据格式
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class Response<T> {
    private T data;
    private String message;
    private String code;
    private boolean success;

    public Response(T data, String message, String code, boolean success) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.success = success;
    }

    public Response() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
