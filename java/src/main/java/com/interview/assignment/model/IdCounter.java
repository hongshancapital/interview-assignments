package com.interview.assignment.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 记录在redis中的生成的id数据值
 */
@Getter
@Setter
public class IdCounter {
  /**
   * 下个批次生成的数据起始值
   */
  private long base;
}
