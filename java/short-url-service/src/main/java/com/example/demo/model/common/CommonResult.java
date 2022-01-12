package com.example.demo.model.common;

import io.swagger.annotations.ApiModel;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
@ApiModel(value = "web统一返回对象", description = "web统一返回对象")
public class CommonResult<T> {
    /**
     * 0-成功，非0-失败
     */
    private  Integer code;
    private  String msg;
    private  T data;

    public CommonResult() {
       super();
    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> paramError(String msg) {
        return fail("参数错误：" + msg);
    }

    public static <T> CommonResult<T> fail() {
        return fail("失败");
    }

    public static <T> CommonResult<T> fail(String msg) {
        return fail(400,null, msg);
    }

    public static <T> CommonResult<T> fail(Integer code,T t, String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(t);
        return result;
    }

    public static <T> CommonResult<T> success(T t) {
        return success(t, "ok");
    }

    public static <T> CommonResult<T> success(T t, String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(t);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
