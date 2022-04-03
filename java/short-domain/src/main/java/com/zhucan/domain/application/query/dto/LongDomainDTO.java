package com.zhucan.domain.application.query.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("长域名数据传输模型")
public class LongDomainDTO {

  @ApiModelProperty("长域名")
  private String domain;

}
