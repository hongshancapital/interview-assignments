package com.alice.shortdomain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/13 17:43
 */
@ApiModel(value = "请求返回封装", description = "请求返回封装")
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = -5858656535335129610L;

    @ApiModelProperty(value = "返回码 200 成功，500失败")
    private int code;
    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回信息描述 e.g. 操作成功")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据，短域名 e.g. : http://localhost/abcd")
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
