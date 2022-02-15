package com.liuwangpan.urlconvert.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Deacription 短域名读取请求参数
 * @author wp_li
 **/
@Data
@ApiModel("短域名读取接口请求体")
public class GetOriginalUrlRequest {
    @ApiModelProperty(value = "短域名", required = true)
    @NotBlank(message = "短域名地址不能为空")
    private String shortUrl;
}