package com.interview.assignment.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.interview.assignment.domain.Application;
import com.interview.assignment.domain.ShortCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 模拟对ShortCode的数据库操作
 */
@Component
public class ShortCodeRepository {
  private static final Cache<String, ShortCode> shortCodeMap = CacheBuilder.newBuilder()
    .maximumSize(100000)
    .build();
  private static final AtomicLong idIncrementer = new AtomicLong(1);  // 模拟生成id

  public void save(ShortCode shortCode) {
    if (null == shortCode.getId() || shortCode.getId() <= 0) {
      shortCode.setId(idIncrementer.getAndIncrement());
    }

    shortCode.setUpdateTime(new Date());
    shortCodeMap.put(shortCode.getCode(), shortCode);
  }

  public ShortCode findByCode(String code) {
    if (StringUtils.isBlank(code)) {
      return null;
    }

    return shortCodeMap.getIfPresent(code);
  }
}
