package com.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: xielongfei
 * @date: 2022/01/11
 * @description: 域名领域对象
 */
@Data
public class DomainDTO {

    /**
     * 短域名链接
     */
    @ApiModelProperty(value = "短域名链接")
    private String shortUrl;

    /**
     * 长域名链接
     */
    @ApiModelProperty(value = "长域名链接")
    private String longUrl;

    public void setLongUrl(String longUrl) {
        if (longUrl == null) {
            throw new IllegalArgumentException("longUrl is null");
        }
        this.longUrl = longUrl;
    }

//    public DomainDTO convertToShort(DomainDTO domainDTO) {
//
//    }
}
