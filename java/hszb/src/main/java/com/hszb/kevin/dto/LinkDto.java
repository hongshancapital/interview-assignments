package com.hszb.kevin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LinkDto {

    @ApiModelProperty(value = "短链接")
    private String shortLink;

    @ApiModelProperty(value = "长链接")
    private String longLink;
}
