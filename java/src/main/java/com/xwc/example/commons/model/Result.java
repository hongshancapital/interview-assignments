package com.xwc.example.commons.model;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 类描述：数据统一的返回格式
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 17:43
 */
@SuppressWarnings("all")
public class Result<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public final static int SUCCESS = 200;
    /**
     * 业务逻辑错误
     */
    public final static int FAILED = 400;

    /**
     * 访问受限（无权限）
     */
    public final static int FORBIDDEN = 403;

    /**
     * 未认证
     */
    public final static int NOT_AUTHEN = 401;

    /**
     * 代码异常
     */
    public final static int ERROR = 500;


    @ApiModelProperty("操作状态: 200-成功; 400-业务失败; 401-未认证;403-访问受限;500-服务器内部错误")
    private int code;

    @ApiModelProperty("操作提示")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    @ApiModelProperty("500的错误描述(开发使用)")
    private String description;

    @ApiModelProperty("服务处理时间")
    private Long timestamp = System.currentTimeMillis(); /*时间戳*/


    /**
     * 处理成功
     */
    public static <T> Result<T> succeed(T data) {
        Result<T> json = new Result<>();
        json.setData(data).setCode(SUCCESS).setMessage("操作成功");
        return json;
    }

    /**
     * 处理成功
     *
     * @param msg 处理成功的提示信息
     * @param <T> 数据正文对象类型
     * @return 结果
     */
    public static <T> Result<T> succeedMsg(String msg) {
        Result<T> json = new Result<>();
        json.setCode(SUCCESS).setMessage(msg);
        return json;
    }


    /**
     * 处理成功
     */
    public static <Void> Result<Void> succeed() {
        Result<Void> json = new Result<>();
        json.setCode(SUCCESS).setMessage("操作成功");
        return json;
    }

    /**
     * 处理失败
     */
    public static <Void> Result<Void> failed(String msg) {
        return new Result<Void>().setCode(FAILED).setMessage(msg);
    }


    /**
     * 处理失败
     */
    public static <Void> Result<Void> failed(String msg, String description) {
        return new Result<Void>().setCode(FAILED).setMessage(msg).setDescription(description);
    }


    /**
     * 操作受限
     */
    public static <Void> Result<Void> forbidden() {
        return new Result<Void>().setCode(FORBIDDEN).setMessage("操作受限");
    }

    /**
     * 操作受限
     */
    public static <Void> Result<Void> forbidden(String message) {
        return new Result<Void>().setCode(FORBIDDEN).setMessage(message);
    }

    /**
     * 未认证
     */
    public static <Void> Result<Void> unauthorized() {
        return new Result<Void>().setCode(NOT_AUTHEN).setMessage("用戶未登录");
    }

    /**
     * 处理失败
     */
    public static <Void> Result<Void> builder(String msg, int code, String description) {
        return new Result<Void>().setCode(code).setMessage(msg).setDescription(description);
    }


    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Result<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Result<T> setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * 请求响应是否成功
     */
    public boolean resultIsSuccess() {
        return this.code == SUCCESS;
    }

    /**
     * 请求响应是否失败
     */
    public boolean resultIsFailed() {
        return this.code == FAILED;
    }

    /**
     * 请求是否出现内部错误
     */
    public boolean resultIsError() {
        return this.code == ERROR;
    }

    /**
     * 请求是否出现操作受限
     */
    public boolean resultIsForbidden() {
        return this.code == FORBIDDEN;
    }

    /**
     * 请求是否未认证
     */
    public boolean resultIsUnauthorized() {
        return this.code == NOT_AUTHEN;
    }

}
