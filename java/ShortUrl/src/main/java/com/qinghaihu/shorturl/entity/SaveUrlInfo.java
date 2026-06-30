package com.qinghaihu.shorturl.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "长域名解析接口")
public class SaveUrlInfo {

    @ApiModelProperty(value = "长域名",required=true)
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
