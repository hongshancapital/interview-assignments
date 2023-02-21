package com.scdtchina.interview.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel(value = "生成短域名请求对象")
public class GetShortUrlReq {
    @ApiModelProperty(value = "长域名")
    @Size(min = 8, message = "长域名长度至少8个字符")
    private String url;
}
