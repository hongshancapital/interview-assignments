package com.example.shorturlservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author xingxing.yu
 * @Date 2022/04/15 16:49
 **/
@ApiModel(description = "查询短链接实体")
public class QueryShortUrlRequest {
    @ApiModelProperty("短链接")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
