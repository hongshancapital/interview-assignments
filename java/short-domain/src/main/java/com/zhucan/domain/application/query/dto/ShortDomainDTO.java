package com.zhucan.domain.application.query.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 22:25
 */
@Data
@ApiModel("短域名传输对象")
public class ShortDomainDTO {

  @ApiModelProperty("短域名")
  private String shortDomain;

  @ApiModelProperty("完整的短域名, 支持直接跳转转发到长域名")
  private String fullShortDomain;

}
