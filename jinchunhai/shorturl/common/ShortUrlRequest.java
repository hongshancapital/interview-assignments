package com.jinchunhai.shorturl.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Description 短域名请求参数
 * @version 1.0
 * @author JinChunhai
 * @time 2021年03月19日
 */

@Api(tags = "短域名请求参数")
public class ShortUrlRequest {

    @NotNull(message = "短域名不能为空")
    @ApiModelProperty("短域名")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    
}
