package com.demo.urlshortlink.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "DTO 类")
public class UrlLongRequest {

    @ApiModelProperty(required = true, notes = "长地址")
    private String longUrl;

    @ApiModelProperty(notes = "到期时间")
    private Date expiresDate;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }
}
