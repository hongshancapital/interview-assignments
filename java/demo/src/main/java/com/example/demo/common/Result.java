package com.example.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dsm
 * @Description: 接口返回数据格式
 * @Date Create in 2021/12/23 10:25
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 返回处理消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 返回数据对象 data
     */
    private T data;

    /**
     * 时间戳
     */
    private long timestamp=System.currentTimeMillis();

    public Result() {

    }

    public void setResult( boolean success, Integer code, T data ) {
        this.setSuccess(success);
        this.setCode(code);
        this.setData(data);
    }
}
