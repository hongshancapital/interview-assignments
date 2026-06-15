package com.example.shorturl.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@ApiModel("通过短域名获取原始域名请求对象")
public class GetOriginalUrlRequestVO implements Serializable {
    private static final long serialVersionUID = 2596188707250979092L;

    @ApiModelProperty(value = "短域名", required = true)
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
