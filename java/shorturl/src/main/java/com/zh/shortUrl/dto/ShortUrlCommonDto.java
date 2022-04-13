package com.zh.shortUrl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author hang.zhang
 * @Date 2022/1/19 15:49
 * @Version 1.0
 */
@ApiModel("短链操作基本入参类")
public class ShortUrlCommonDto {
    @ApiModelProperty("短链接 str")
    private String shortUrl;
    @ApiModelProperty("长链接 str")
    private String longUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
