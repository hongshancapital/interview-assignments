package com.cn.scdt.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回状态码
 * @param <T>
 */
@Data
public class ResponseDto<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;


    public ResponseDto(){
    }

    public ResponseDto(Integer code, String message, T data){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}