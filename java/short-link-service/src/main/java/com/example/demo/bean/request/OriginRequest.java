package com.example.demo.bean.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author shenbing
 * @since 2022/1/8
 */
public class OriginRequest {

    @ApiModelProperty("短链接")
    @NotBlank(message = "短链接不能为空")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
