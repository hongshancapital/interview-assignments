package com.jinchunhai.shorturl.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Description 长域名请求参数
 * @version 1.0
 * @author JinChunhai
 * @time 2021年03月19日
 */

@Api(tags = "长域名请求参数")
public class LongUrlRequest {

    @NotNull(message = "长域名不能为空")
    @ApiModelProperty("长域名")
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
    
}
