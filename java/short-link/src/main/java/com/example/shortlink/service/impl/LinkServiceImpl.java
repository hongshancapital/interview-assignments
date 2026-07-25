package com.example.shortlink.service.impl;

import com.example.shortlink.config.AppConfig;
import com.example.shortlink.service.KeyService;
import com.example.shortlink.service.LinkService;
import com.example.shortlink.util.BinaryConversionUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
@Service
@Slf4j
public class LinkServiceImpl implements LinkService, InitializingBean {

  @Autowired
  private KeyService keyService;

  private List<Cache<String, String>> data;

  private final AppConfig appConfig;

  private final Integer guavaNum;

  @Autowired
  public LinkServiceImpl(AppConfig appConfig) {
    this.appConfig = appConfig;
    guavaNum = appConfig.GUAVA_NUM;
  }

  @Override
  public String longChangShort(String longLink) {
    long id = keyService.getUniqueId();
    Cache<String, String> cache = getCache(id);
    String key = BinaryConversionUtil.encode(id);
    cache.put(key, longLink);
    return appConfig.MACHINE_CODE + key;
  }

  @Override
  public String shortChangeLong(String shortLink) {
    String key = shortLink.substring(appConfig.MACHINE_CODE.length());
    long id = BinaryConversionUtil.decode(key);
    Cache<String, String> cache = getCache(id);
    return StringUtils.isEmpty(key) ? null : cache.getIfPresent(key);
  }

  /**
   * 获取cache
   *
   * @param id id
   * @return cache
   */
  private Cache<String, String> getCache(long id) {
    long k = (id == 0) ? 0 : id ^ (id >>> KeyService.BIT);
    return data.get((int) (k % guavaNum));
  }

  @Override
  public void afterPropertiesSet() {
    data = new ArrayList<>(guavaNum);
    for (int i = 0; i < guavaNum; i++) {
      Cache<String, String> cache = initCache();
      data.add(i, cache);
    }
  }

  /**
   * 初始化cache
   *
   * @return cache
   */
  private Cache<String, String> initCache() {
    return CacheBuilder.newBuilder()
        .concurrencyLevel(10)
        .initialCapacity(100)
        .maximumSize(10000000)
        .recordStats()
        .expireAfterWrite(appConfig.EXPIRE_SEC, TimeUnit.SECONDS)
        .removalListener(
            notice -> log.info("key:" + notice.getKey() + " value:" + notice.getValue() + " cause:"
                + notice.getCause()))
        .build();
  }
}
