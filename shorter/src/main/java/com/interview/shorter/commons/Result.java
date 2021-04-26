package com.interview.shorter.commons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-26
 */
@ApiModel(value = "返回说明")
public class Result implements Serializable {
    @ApiModelProperty(value = "成功标识；-1：成功；其他的错误码:失败")
    private int code;
    @ApiModelProperty(value = "返回信息；ok：成功；其他的错误信息:失败")
    private String message;
    @ApiModelProperty(value = "返回数据；如果成功则返回所接受的数据，错误的情况下返回null")
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
