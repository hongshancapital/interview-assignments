package com.wangxinyu.interview.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("长短域名对应关系对象")
public class UrlDTO {
    @ApiModelProperty("长域名")
    private String longURL;
    @ApiModelProperty("短域名")
    private String shortURL;

    public UrlDTO(String longURL, String shortURL) {
        this.longURL = longURL;
        this.shortURL = shortURL;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    @Override
    public String toString() {
        return "UrlDTO{" +
                "longURL='" + longURL + '\'' +
                ", shortURL='" + shortURL + '\'' +
                '}';
    }
}
