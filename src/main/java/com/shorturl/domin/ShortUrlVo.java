package com.shorturl.domin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("短链接生成服务VO")
public class ShortUrlVo {

    @ApiModelProperty("生成的短链接")
    private String shortUrl;

    @ApiModelProperty("返回的长链接")
    private String longUrl;

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
