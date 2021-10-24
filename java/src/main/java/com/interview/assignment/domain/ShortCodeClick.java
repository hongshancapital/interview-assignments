package com.interview.assignment.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 按小时统计每个短码的统计数据
 */
@Getter
@Setter
public class ShortCodeClick {

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
  private String shortCode;

  /**
   * 日期，格式：yyyy-MM-dd
   */
  private String day;

  /**
   * 小时，格式：HH
   */
  private String hour;

  /**
   * 点击次数
   */
  private Long times;

}
