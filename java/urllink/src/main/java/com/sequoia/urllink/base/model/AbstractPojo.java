package com.sequoia.urllink.base.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 操作基本对象
 * @author liuhai
 * @date 2022.4.15
 */
@Setter
@Getter
public abstract class AbstractPojo<T extends Serializable> implements Serializable {
  /**
   * 主键
   */
  private T id;
  // 前台传参：当前页数
  private Integer pageNum;
  // 前台传参：页面数量
  private Integer pageSize;
}
