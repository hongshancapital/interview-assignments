package com.tinyurl.entity;

import io.swagger.annotations.ApiModelProperty;

public class TinyUrlEntity {
    @ApiModelProperty(value = "网址")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
