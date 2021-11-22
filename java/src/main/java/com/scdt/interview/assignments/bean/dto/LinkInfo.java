package com.scdt.interview.assignments.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LinkInfo {
    @ApiModelProperty("长连接url")
    private String longUrl;

    @ApiModelProperty("短连接url")
    private String shortUrl;
}
