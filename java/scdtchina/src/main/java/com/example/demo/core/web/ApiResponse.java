package com.example.demo.core.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "ApiResponse", description = "api响应体")
public class ApiResponse<T> implements Serializable {

    /**
     * 返回数据对象
     */
    @ApiModelProperty(value = "返回数据对象")
    private T data;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码", required = true)
    private Integer code;

    /**
     * 错误原因
     */
    @ApiModelProperty(value = "错误原因", required = true)
    private String message;

    /**
     * 构造函数
     */
    public ApiResponse() {

    }

    public ApiResponse(MessageCode messageCode) {
        this(messageCode.getCode(), messageCode.getMsg());
    }

    public ApiResponse(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public static ApiResponse buildFail(int code, String message) {
        return new ApiResponse(code, message);
    }

    public static ApiResponse buildFail(MessageCode messageCode) {
        return buildFail(messageCode.getCode(), messageCode.getMsg());
    }

    public static ApiResponse buildSuccess() {
        return new ApiResponse(ResponseCode.SUCCESS);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}