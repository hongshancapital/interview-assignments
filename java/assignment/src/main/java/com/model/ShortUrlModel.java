package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("短域名接口模型")
@Data
public class ShortUrlModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "短域名", example = "http://t.cn/ABCDEF")
    private String shortUrlStr;
    @ApiModelProperty(value = "长域名", example = "www")
    private String longUrlStr;
    @ApiModelProperty(value = "code", example = "0")
    private Integer code;
    @ApiModelProperty(value = "message", example = "success")
    private String message;
}
