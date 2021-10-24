package com.interview.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 标记当前调用方的业务线
 */
@Getter
@Setter
public class Application {

  /**
   * 主键id
   */
  private Long id;

  /**
   * 业务线标识
   */
  private String appId;

  /**
   * 业务线名称
   */
  private String name;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;

}
