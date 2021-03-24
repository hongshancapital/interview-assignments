package com.snail.shorturlservice.common.response;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("响应结果实体")
public class ResultBean<T> implements Serializable {
    @ApiModelProperty("响应码")
    private int code;
    @ApiModelProperty("响应信息")
    private String msg;
    @ApiModelProperty("响应数据")
    private T data;

    public ResultBean() {
        this(ResultCode.SUCCESS);
    }

    public ResultBean(ResultCode resultCode) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
    }

    public ResultBean(ResultCode resultCode, String msg) {
        this.code = resultCode.code;
        this.msg = msg;
    }

    public static final ResultBean success() {
        return new ResultBean();
    }

    public static final  <T> ResultBean<T> success(T data) {
        ResultBean<T> resultBean = new ResultBean<T>();
        resultBean.setData(data);
        return resultBean;
    }

    public static final ResultBean fail(ResultCode resultCode) {
        return new ResultBean(resultCode);
    }

    public static ResultBean fail(ResultCode resultCode, String message) {
        return new ResultBean(resultCode, message);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("msg", msg)
                .add("data", data)
                .toString();
    }
}
