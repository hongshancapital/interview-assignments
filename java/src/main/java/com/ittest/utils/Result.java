package com.ittest.utils;

import lombok.Data;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/21 10:45
 * @Description: 结果封装类
 */
@Data
public class Result<T> {

    /**
     * 结构响应吗
     */
    private int code;
    /**
     * 结果响应消息
     */
    private String message;
    /**
     * 结果返回值
     */
    private T data;

    /**
     * 成功时调用
     *
     * @param data 返回结果
     * @param <T>  泛型
     * @return 返回值
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时调用
     *
     * @param code    响应吗
     * @param message 响应消息
     * @param <T>     泛型
     * @return 返回值
     */
    public static <T> Result error(int code, String message) {
        return new Result<T>(code, message);
    }

    /**
     * @param data data
     */
    private Result(T data) {
        this.setCode(0);
        this.setMessage("SUCCESS");
        this.setData(data);
    }

    /**
     * @param code    响应吗
     * @param message 响应消息
     */
    private Result(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}
