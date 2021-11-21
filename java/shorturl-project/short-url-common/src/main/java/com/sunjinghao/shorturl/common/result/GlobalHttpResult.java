package com.sunjinghao.shorturl.common.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sunjinghao
 */
@ApiModel(description = "http返回结果")
@Data
public class GlobalHttpResult<T> implements Serializable {
    private static final long serialVersionUID = -4464684848051517189L;

    @ApiModelProperty(value = "返回编码:200->成功;500->失败",name = "code")
    private Integer code;

    @ApiModelProperty(value = "提示信息",name = "message")
    private String message;

    @ApiModelProperty(value = "返回对象",name = "data")
    private T data;

    public GlobalHttpResult(T data){
        this(GlobalResultCodeEnum.REQUEST_OK, data);
    }

    public GlobalHttpResult(GlobalResultCodeEnum resultCodeEnum, T data){
        this.data = data;
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMsg();
    }

    public GlobalHttpResult(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    public GlobalHttpResult(Integer code, String message, T data){
        this.data = data;
        this.code=code;
        this.message=message;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static<T> GlobalHttpResult<T> createSuccess(T result){
        return new GlobalHttpResult(result);
    }

    public GlobalHttpResult(GlobalResultCodeEnum status) {
        this.code = status.getCode();
        this.message = status.getMsg();
        this.data = null;
    }
}