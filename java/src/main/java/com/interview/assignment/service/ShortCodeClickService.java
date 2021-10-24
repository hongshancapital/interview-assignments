package com.interview.assignment.service;

import com.interview.assignment.domain.ShortCode;

public interface ShortCodeClickService {

  /**
   * 记录当前短码的点击数据
   *
   * @param shortCode 短码数据
   */
  void record(ShortCode shortCode);
}
