package com.wangxinyu.interview.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("通用返回类型")
public class CommonReturnType<T> {
    @ApiModelProperty("状态码，0代表正常，非零代表异常")
    private Integer code;//0代表正常，非零代表异常
    @ApiModelProperty("错误信息")
    private String errorMsg;
    @ApiModelProperty("数据信息")
    private T data;

    public CommonReturnType(Integer code, String errorMsg, T data) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonReturnType{" +
                "code=" + code +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
