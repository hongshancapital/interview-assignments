package com.scdt.tinyurl.common;

import lombok.Data;

@Data
public class ResponseResult<T> {

    // 返回码
    private String code;

    // 返回信息描述
    private String msg;

    // 返回内容
    private T data;
}
