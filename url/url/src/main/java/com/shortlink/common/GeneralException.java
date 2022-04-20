package com.shortlink.common;

import lombok.Data;

/**
 * 定义一个异常类
 */

public class GeneralException extends RuntimeException{


    private Integer code;
    private String desc;


    public GeneralException(Status status) {
        this.code = status.getCode();
        this.desc = status.getDesc();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
