package com.wangxiao.shortlink.infrastructure.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    public Result(boolean success, int code) {
        this.setSuccess(success);
        this.setCode(code);
    }

    public Result(boolean success, int code, T data) {
        this.setSuccess(success);
        this.setCode(code);
        this.setData(data);
    }

    public Result(boolean success, int code, String msg) {
        this.setSuccess(success);
        this.setCode(code);
        this.setData(data);
        this.setMsg(msg);
    }


    /**
     * 请求是否成功
     * true:成功
     * false：失败
     */
    private boolean success;

    /**
     * 状态码
     * 成功：200
     * 失败：其他
     */
    private int code;

    /**
     * 失败状态码描述
     * 如果成功不返回
     * 失败返回状态码对应的msg消息
     */
    private String msg;

    /**
     * 请求数据的结果
     */
    private T data;


    public static <T> Result<T> success() {
        return new Result<T>(true, 200);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, 200, data);
    }


    public static <T> Result<T> fail(ErrorEnum errorEnum) {
        return new Result<T>(false, errorEnum.getCode(), errorEnum.getReason());
    }

    public static <T> Result<T> fail(ErrorEnum errorEnum, String msg) {
        return new Result<T>(false, errorEnum.getCode(), msg);
    }
}
