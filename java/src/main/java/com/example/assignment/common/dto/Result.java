package com.example.assignment.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result success(T data) {
        this.setData(data);
        this.setCode(ResponseEnum.SUCCESS.getCode());
        this.setMessage(ResponseEnum.SUCCESS.getMessage());
        return this;
    }

    public Result failed(ResponseEnum responseEnum) {
        this.setCode(responseEnum.getCode());
        this.setMessage(responseEnum.getMessage());
        return this;
    }
}
