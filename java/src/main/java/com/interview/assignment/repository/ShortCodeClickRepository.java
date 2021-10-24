package com.interview.assignment.repository;

import com.interview.assignment.domain.ShortCodeClick;
import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
@Component
public class ShortCodeClickRepository {

  final Map<String, ShortCodeClick> timeRegionMap = new ConcurrentHashMap<>();

  private static final AtomicLong idIncrementer = new AtomicLong(1);  // 模拟生成id

  public void record(String appId, String code, String day, String hour) {
    if (StringUtils.isAnyBlank(appId, code, day, hour)) {
      log.error("param is blank, appId: {}, code: {}, day: {}, hour: {}", appId, code, day, hour);
      throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
    }

    String key = String.format("%s-%s-%s", code, day, hour);  // 模拟有code、day、hour的联合索引

    timeRegionMap.compute(key, (k, v) -> {
      if (v == null) {
        v = new ShortCodeClick();
        v.setAppId(appId);
        v.setShortCode(code);
        v.setDay(day);
        v.setHour(hour);
        v.setTimes(0L);
        v.setId(idIncrementer.getAndIncrement());
      }

      v.setTimes(v.getTimes() + 1);
      return v;
    });
  }
}
