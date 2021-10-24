package com.interview.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 记录短链的信息
 */
@Getter
@Setter
public class ShortCode {

  /**
   * 主键id
   */
  private Long id;

  /**
   * 业务线id
   */
  private String appId;

  /**
   * 短码
   */
  private String code;

  /**
   * 长链接
   */
  private String url;

  /**
   * 短链类型，具体的值参见CodeType
   */
  private String type;

  /**
   * 时效类型，具体的值参见DurationType
   */
  private String durationType;

  /**
   * 时效类型为限时情况时，开始时间
   */
  private Date startTime;

  /**
   * 时效类型为限时情况时，结束时间
   */
  private Date endTime;

  /**
   * 短码创建时间
   */
  private Date createTime;

  /**
   * 短码更新时间
   */
  private Date updateTime;
}
