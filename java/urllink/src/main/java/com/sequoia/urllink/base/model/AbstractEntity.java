package com.sequoia.urllink.base.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Setter
@Getter
public abstract class AbstractEntity<T extends Serializable> implements Serializable {
  /**
   * 备用字段
   */
  protected Integer intAttr1;
  protected Integer intAttr2;
  protected Integer intAttr3;
  protected String strAttr1;
  protected String strAttr2;
  protected String strAttr3;
  /**
   * 主键
   */
  private T id;
}
