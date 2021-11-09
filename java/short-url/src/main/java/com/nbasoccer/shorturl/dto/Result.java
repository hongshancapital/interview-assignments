package com.nbasoccer.shorturl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("响应结果类")
public class Result implements Serializable {

    //接口消耗时间
    @ApiModelProperty("接口响应耗时")
    private Long took;

    //请求code
    @ApiModelProperty("返回码")
    private Integer code;
    //返回请求信息
    @ApiModelProperty("返回信息说明")
    private String message;
    //请求结果
    @ApiModelProperty("返回结果数据")
    private Object data;

    public Result(){}

    public Result(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
