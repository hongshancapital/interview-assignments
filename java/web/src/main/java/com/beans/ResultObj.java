package com.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "返回响应数据")
public class ResultObj<T> {

    /**
     * 1 成功
     * 2 失败
     */
    @ApiModelProperty(value = "是否成功 1 成功 2 失败",required=true)
    private Integer code ;

    /**
     * 消息
     */
    @ApiModelProperty(value = "是否成功信息", required=true)
    private String msg ;

    /**
     * 结果
     */
    @ApiModelProperty(value = "返回结果")
    private T result ;

    public ResultObj(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultObj(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
