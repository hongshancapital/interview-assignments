package com.scdt.shortenurl.common;

import com.scdt.shortenurl.common.enums.ErrorCodeEnum;

/**
 * @Description 统一返回结果
 * @Author chenlipeng
 * @Date 2022/3/7 2:14 下午
 */
public class Response<T> {

    private Boolean success;

    private String code;

    private String message;

    private T data;

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<T>();
        response.setSuccess(true);
        response.setCode(ErrorCodeEnum.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> failure(ErrorCodeEnum errorCode) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return response;
    }

    public static <T> Response<T> failure(ErrorCodeEnum errorCode, String errMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(errorCode.getCode());
        response.setMessage(errMsg);
        return response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
