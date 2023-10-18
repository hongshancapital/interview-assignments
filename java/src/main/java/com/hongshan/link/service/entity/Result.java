package com.hongshan.link.service.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author heshineng
 * created by 2022/8/8
 * Controller 层返回的的数据结果
 */
@ApiModel(value = "Result", description = "标准返回结果")
public class Result<T> implements Serializable {

    /**
     * 成功的code
     */
    private final static int SUCCESS_CODE = 1;

    /**
     * 失败的code
     */
    private final static int FAIL_CODE = 0;

    @ApiModelProperty(value = "真正的结果")
    private T data;

    @ApiModelProperty(value = "结果code，1=成功，0=失败")
    private int code;

    @ApiModelProperty(value = "code对应的结果集")
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result<>();
        result.setCode(FAIL_CODE);
        result.setMsg("失败");
        return result;
    }

}
