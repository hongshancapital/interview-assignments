package com.interview.assignment.response;

import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("短码查询响应")
public class ShortCodeQueryResponse {

  /**
   * 业务线id
   */
  @ApiModelProperty("业务线id")
  private String appId;

  /**
   * 短码
   */
  @ApiModelProperty("短码")
  private String code;

  /**
   * 长链接
   */
  @ApiModelProperty("长链接")
  private String url;

  /**
   * 短链类型，具体的值参见CodeType
   */
  @ApiModelProperty("短码类型")
  private CodeType type;

  /**
   * 时效类型，具体的值参见DurationType
   */
  @ApiModelProperty("时效类型")
  private DurationType durationType;

  /**
   * 时效类型为限时情况时，开始时间
   */
  @ApiModelProperty("时效开始时间")
  private Date startTime;

  /**
   * 时效类型为限时情况时，结束时间
   */
  @ApiModelProperty("时效结束时间")
  private Date endTime;

}
