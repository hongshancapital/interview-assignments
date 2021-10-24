package com.interview.assignment.filter.impl;

import com.interview.assignment.enums.DurationType;
import com.interview.assignment.filter.ShortCodeGenerateFilter;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 对生成短码的时效性进行校验：1. 开始时间不能为空；2. 结束时间不能为空；3. 开始时间小于结束时间；
 */
@Log4j2
@Order(10)
@Component
public class ShortCodeDurationFilter implements ShortCodeGenerateFilter {
  @Override
  public boolean filter(ShortCodeGenerateRequest request) {
    if (request.getDurationType() == null || request.getDurationType() == DurationType.PERMANENT) {
      return true;
    }

    boolean result = request.getStartTime() != null && request.getEndTime() != null
      && request.getStartTime().before(request.getEndTime());
    if (!result) {
      log.info("duration param is error, appId: {}, url: {}, durationType: {}, startTime: {}, endTime: {}",
        request.getAppId(), request.getUrl(), request.getDurationType(), request.getStartTime(), request.getEndTime());
    }

    return result;
  }
}
