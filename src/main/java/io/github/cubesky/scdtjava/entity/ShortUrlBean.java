package io.github.cubesky.scdtjava.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "io-github-cubesky-scdtjava-entity-ShortUrlBean")
public class ShortUrlBean {
    @ApiModelProperty(value = "短 URL")
    private String shortUrl;

    public ShortUrlBean() {}

    public ShortUrlBean(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
