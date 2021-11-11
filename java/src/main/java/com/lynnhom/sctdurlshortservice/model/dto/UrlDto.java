package com.lynnhom.sctdurlshortservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 短链接生成请求入参实体类
 * @author: Lynnhom
 * @create: 2021-10-28 10:48
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短链接生成服务请求参数")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlDto {
    @ApiModelProperty("接入服务的id")
    private String appId;
    @ApiModelProperty("token信息")
    private String token;
    @ApiModelProperty("原始链接")
    private String url;
    @ApiModelProperty("短链接失效时间")
    private String expireDate;
}
