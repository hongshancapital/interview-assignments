package com.scdtchina.interview.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel(value = "根据短域名返回长域名请求对象")
public class GetLongUrlReq {
    @ApiModelProperty(value = "短域名")
    @Size(min = 1, message = "短域名长度至少1个字符")
    private String url;
}
