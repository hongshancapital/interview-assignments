package com.qinghaihu.shorturl.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "短域名解析接口")
public class ResolveUrlInfo {

    @ApiModelProperty(value = "短域名",required=true)
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
