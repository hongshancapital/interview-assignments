package com.tinyurl.response;

import io.swagger.annotations.ApiModelProperty;

public class ResponseEntity<T> {

    @ApiModelProperty(value = "成功标识；true：成功；false:失败", position = 1)
    private Boolean success;

    @ApiModelProperty(value = "返回状态码", position = 2)
    private String code;

    @ApiModelProperty(value = "描述信息", position = 4)
    private String message;

    @ApiModelProperty(value = "返回对象", position = 5)
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
