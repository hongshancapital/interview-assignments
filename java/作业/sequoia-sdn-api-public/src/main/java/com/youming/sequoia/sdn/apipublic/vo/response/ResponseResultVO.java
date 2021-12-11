package com.youming.sequoia.sdn.apipublic.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("通用接口返回对象")
public class ResponseResultVO<T> {

    /**
     * 返回码
     */
    @ApiModelProperty(required = true, notes = "结果码,正常时为0", example = "0")
    private int code;

    /**
     * 信息，code非0时有值
     */
    @ApiModelProperty(required = true, notes = "系统提示信息，在错误时给出提示，无错误时为空", example = "")
    private String msg;
    /**
     * 数据
     */
    @ApiModelProperty(required = true, notes = "返回的数据，是一个对象", example = "{\"key\":\"value\"}")
    private T data;

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
}
