package com.example.demo.domainnameservice.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * api result.
 *
 * @author laurent
 * @date 2021-12-11 上午10:18
 */
@Getter
@Setter
public class ApiResult<T> {

    private Integer code;

    private String msg;

    private T data;

    public ApiResult() {
        this.code = 0;
        this.msg = "";
    }

    public ApiResult(final T data) {
        this.code = 0;
        this.msg = "";
        this.data = data;
    }

}
