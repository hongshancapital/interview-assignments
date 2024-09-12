package com.shortlink.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
public class Result<T> {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("描述信息")
    private String msg;

    @ApiModelProperty("数据返回")
    private T data;


    public Result() {}

    public Result(T data) {
        this.code = Status.SUCCESS.getCode();
        this.msg = Status.SUCCESS.getDesc();
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }
}
