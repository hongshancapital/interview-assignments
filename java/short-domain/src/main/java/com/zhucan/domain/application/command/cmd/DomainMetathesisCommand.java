package com.zhucan.domain.application.command.cmd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:42
 */
@Data
@ApiModel("短域名转换命令模型")
public class DomainMetathesisCommand {

  @ApiModelProperty("长域名")
  private String domain;

}
