package io.github.cubesky.scdtjava.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "io-github-cubesky-scdtjava-entity-UrlBean")
public class UrlBean {
    @ApiModelProperty(value = "短 URL")
    private String shortUrl;
    @ApiModelProperty(value = "长 URL")
    private String longUrl;
    public UrlBean() {

    }

    public UrlBean(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

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
