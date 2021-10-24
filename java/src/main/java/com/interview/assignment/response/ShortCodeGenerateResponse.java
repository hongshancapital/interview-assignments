package com.interview.assignment.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 生成的短链response
 */
@Getter
@Setter
@ApiModel("短码生成的响应")
public class ShortCodeGenerateResponse {
  /**
   * 短链
   */
  @ApiModelProperty("生成的短码")
  private String shortCode;
}
