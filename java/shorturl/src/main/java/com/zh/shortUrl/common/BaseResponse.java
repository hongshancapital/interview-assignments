package com.zh.shortUrl.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author hang.zhang
 * @Date 2022/1/19 14:34
 * @Version 1.0
 */
@ApiModel("系统基本出参")
public class BaseResponse<T> {
    @ApiModelProperty("响应码")
    private int code = 0;
    @ApiModelProperty("响应描述")
    private String message = "success";
    @ApiModelProperty("响应内容")
    private T data;

    public BaseResponse(T data){
        this.data=data;
    }
    public BaseResponse(){}

    public BaseResponse(int code,String msg,T data){
        this.code=code;
        this.message=msg;
        this.data=data;
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
