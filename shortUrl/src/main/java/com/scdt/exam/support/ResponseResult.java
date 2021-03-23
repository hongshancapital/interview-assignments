package com.scdt.exam.support;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一返回结果处理
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {
    /**
     * 成功
     */
    public static final int SUCCESS = 200;
    /**
     * 失败
     */
    public static final int FAILED = 400;
    /**
     * 错误
     */
    public static final int ERROR = 500;
    /**
     * 返回的code值
     */
    @ApiModelProperty("返回的code值,200:成功，400:失败，500:错误")
    private int code;
    /**
     * 返回消息
     */
    @ApiModelProperty("返回的消息")
    private String message;
    /**
     * 具体数据
     */
    @ApiModelProperty("返回的数据")
    private T data;

    public ResponseResult() {
        this(SUCCESS, "操作成功");
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(String message, T data) {
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

    public static ResponseResult success() {
        return new ResponseResult(SUCCESS, "操作成功");
    }

    public static ResponseResult success(String message) {
        return new ResponseResult(SUCCESS, message);
    }

    public static <T> ResponseResult<T> successWithData(T data) {
        return new ResponseResult<>(SUCCESS, "操作成功", data);
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<>(SUCCESS, message, data);
    }

    public static ResponseResult failed() {
        return new ResponseResult(FAILED, "操作失败");
    }

    public static ResponseResult failed(String message) {
        return new ResponseResult(FAILED, message);
    }

    public static <T> ResponseResult<T> failedWithData(T data) {
        return new ResponseResult<>(FAILED, "操作失败", data);
    }

    public static <T> ResponseResult<T> failed(String message, T data) {
        return new ResponseResult<>(FAILED, message, data);
    }

    public static ResponseResult error() {
        return new ResponseResult(ERROR, "系统错误");
    }

    public static ResponseResult error(String message) {
        return new ResponseResult(ERROR, message);
    }

    public static <T> ResponseResult<T> errorWithData(T data) {
        return new ResponseResult<>(ERROR, "系统错误", data);
    }

    public static <T> ResponseResult<T> error(String message, T data) {
        return new ResponseResult<>(ERROR, message, data);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
