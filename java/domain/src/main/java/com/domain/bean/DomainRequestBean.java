package com.domain.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：ji
 * @description：
 */
@ApiModel("短域名读取接口请求对象封装类")
public class DomainRequestBean {
    @ApiModelProperty("短域名URL")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
