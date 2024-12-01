package com.hongshan.interfacejob.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("短域名对象")
public class ShortUrlVo implements Serializable {

    private static final long serialVersionUID = 8595399514967770543L;

    @ApiModelProperty(value = "短域名", example = "t.cn/bd")
    private String shortUrl;

    @ApiModelProperty(value = "长域名", example = "www.baidu.com")
    private String longUrl;

    @ApiModelProperty(value = "code", example = "0")
    private Integer code;

    @ApiModelProperty(value = "提示信息", example = "success")
    private String msg;

}