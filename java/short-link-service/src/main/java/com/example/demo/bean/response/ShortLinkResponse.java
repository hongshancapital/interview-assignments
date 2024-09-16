package com.example.demo.bean.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author shenbing
 * @since 2022/1/8
 */
public class ShortLinkResponse {

    @ApiModelProperty("短链接")
    private String shortUrl;

    @ApiModelProperty("原始链接")
    private String originalUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
