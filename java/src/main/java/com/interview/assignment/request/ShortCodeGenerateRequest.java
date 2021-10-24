package com.interview.assignment.request;

import com.interview.assignment.annotation.AppId;
import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 生成短码request
 */
@Getter
@Setter
@ApiModel("短码生成请求")
public class ShortCodeGenerateRequest {
  /**
   * 业务线id，需提前申请
   */
  @AppId
  @ApiModelProperty("业务线id")
  private String appId;

  /**
   * 需要转换的长链接
   */
  @ApiModelProperty("长链接")
  @Pattern(regexp = "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$", message = "url is invalid!")
  private String url;

  /**
   * 短码类型
   */
  @ApiModelProperty("短码类型")
  @NotNull(message = "type cannot be null!")
  private CodeType type = CodeType.URL_REDIRECT;

  /**
   * 时效类型
   */
  @ApiModelProperty("时效类型")
  private DurationType durationType = DurationType.PERMANENT;

  /**
   * 时效开始时间
   */
  @ApiModelProperty("时效开始时间")
  private Date startTime;

  /**
   * 时效结束时间
   */
  @ApiModelProperty("时效结束时间")
  private Date endTime;
}
