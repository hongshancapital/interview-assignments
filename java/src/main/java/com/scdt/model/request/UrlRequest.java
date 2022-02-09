package com.scdt.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlRequest {

    @ApiModelProperty("当前请求的连接")
    private String url;
}
