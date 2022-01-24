package com.liuwangpan.urlconvert.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Deacription short url request
 * @author wp_li
 **/
@Data
@ApiModel("短域名存储接口请求体")
public class UrlConvertRequest {
    @ApiModelProperty(value = "长域名", required = true)
    @NotBlank(message = "长域名地址不能为空")
    private String longUrl;
}