package com.example.shorturlservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author xingxing.yu
 * @Date 2022/04/15 16:49
 **/
@ApiModel(description = "保存长链接实体")
public class SaveLongUrlRequest {
    @ApiModelProperty("长链接")
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
