package com.interview.assignment.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("短码查询请求")
public class ShortCodeQueryRequest {

  /**
   * 短码
   */
  @ApiModelProperty("短码")
  @NotBlank(message = "shortCode cannot be blank!")
  private String shortCode;
}
