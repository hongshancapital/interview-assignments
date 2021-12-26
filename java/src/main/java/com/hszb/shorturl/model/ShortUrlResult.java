package com.hszb.shorturl.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 3:23 下午
 * @Version: 1.0
 * @Description:
 */

public class ShortUrlResult {

    @ApiModelProperty(value = "短链接地址", example = "http://hszb.cn/b3GC")
    private String shortUrl;

    @ApiModelProperty(value = "短链接code标识，可用于请求原始链接", example = "b3GC")
    private String shortCode;

    @ApiModelProperty(value = "原始长链接", example = "http://www.hszb.com/business/user/info")
    private String longUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
