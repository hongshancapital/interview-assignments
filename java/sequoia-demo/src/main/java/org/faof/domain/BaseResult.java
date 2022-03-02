package org.faof.domain;

import io.swagger.annotations.ApiModel;

@ApiModel("返回值")
public class BaseResult {

    private String longUrl;

    private String shortUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
