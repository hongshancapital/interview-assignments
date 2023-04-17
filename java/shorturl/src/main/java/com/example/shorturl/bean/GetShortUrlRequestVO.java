package com.example.shorturl.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@ApiModel("通过原始域名获取短域名请求对象")
public class GetShortUrlRequestVO implements Serializable {

    private static final long serialVersionUID = -206191625591434545L;


    @ApiModelProperty(value = "短域名", required = true)
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
