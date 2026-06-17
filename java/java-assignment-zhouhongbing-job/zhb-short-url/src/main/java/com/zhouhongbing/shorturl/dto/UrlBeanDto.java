package com.zhouhongbing.shorturl.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @Author 海纳百川zhb
 **/
@Data
public class UrlBeanDto {

    @ApiModelProperty("长域名地址")
    private String longUrl;

    @ApiModelProperty("短域名地址")
    private String shortUrl;
}
