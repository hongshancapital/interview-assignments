package com.interview.assignment.generator;

/**
 * 通过id生成短码的处理器，有如下几种：
 */
public interface ShortCodeHandler extends Generator<Long, String> {

  /**
   * 对输入的shortCode根据当前规则进行校验
   *
   * @param shortCode 输入的短码
   * @return 校验是否通过
   */
  boolean check(String shortCode);
}
