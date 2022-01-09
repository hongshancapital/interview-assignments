package com.tizzy.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "UrlLongRequest", description = "长链接参数")
public class UrlLongRequest {

    @ApiModelProperty(required = true, value = "长链接", notes = "转化成短链的长链接")
    @NotNull(message = "longUrl不能为空")
    @Size(max = 1024, min = 9, message = "longUrl长度必须大于8, longUrl长度必须小于等于1024")
    private String longUrl;

    @ApiModelProperty(value = "失效时间戳", notes = "长链对应短链在服务端的失效时间")
    private long expiresTime;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }
}
