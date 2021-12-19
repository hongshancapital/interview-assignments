package com.my.linkapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel("长短链接传输对象")
public class LinkShortRequestDto {
    @ApiModelProperty("长短链接内容")
    private String link;
}
