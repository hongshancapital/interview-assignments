package com.interview.assignment.service;

import com.interview.assignment.domain.Application;

public interface ApplicationService {
  /**
   * 根据app id查询Application数据
   *
   * @param appId 业务线id
   * @return 记录业务线的domain
   */
  Application findByAppId(String appId);
}
