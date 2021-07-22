package com.hello.tinyurl.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: Shuai
 * @date: 2021-7-19 13:52
 * @description: 通用返回值
 */
@ApiModel(value = "Result", description = "通用返回值")
public class Result<T> {

    @ApiModelProperty("status")
    private int status;

    @ApiModelProperty("description")
    private String description;

    @ApiModelProperty("data")
    private T data;

    public Result() {
    }

    public Result(int status, String description, T data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public T getData() {
        return data;
    }
}