package com.zhucan.domain.infrastructure.cache;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 23:20
 */
@Service
public class LocalCache implements DomainCache {

  private static final ConcurrentHashMap<String, String> domainCache = new ConcurrentHashMap<>();

  public void save(String shortDomain, String LongDomain) {
    domainCache.put(shortDomain, LongDomain);
  }

  public String getLongDomain(String shortDomain) {
    return domainCache.get(shortDomain);
  }

}
