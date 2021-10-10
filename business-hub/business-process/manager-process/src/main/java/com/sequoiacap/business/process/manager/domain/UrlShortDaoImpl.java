package com.sequoiacap.business.process.manager.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *@ClassName: UrlShortDaoImpl
 *@Description: UrlShortDaoImpl
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
@Component
public class UrlShortDaoImpl implements UrlShortDao{

  @Value("${short.url.userCacheName:shortUrlCache}")
  private String shortUrlCacheName;

  @Value("${long.url.userCacheName:longUrlCache}")
  private String longUrlCacheName;

  @Autowired
  private CacheManager cacheManager;
  //key:shortUrl value:longUrl
  private Cache shortUrlCache;
  //key:longUrl value:shortUrl
  private Cache longUrlCache;

  @PostConstruct
  public void init(){
    this.shortUrlCache = cacheManager.getCache(shortUrlCacheName);
    this.longUrlCache = cacheManager.getCache(longUrlCacheName);
  }

  public void save(String url, String shortUrl) {
    this.shortUrlCache.put(shortUrl,url);
    this.longUrlCache.put(url,shortUrl);
  }

  public String get(String shortUrl) {
    return this.shortUrlCache.get(shortUrl,String.class);
  }

  public String getShortUrlByLongUrl(String longUrl) {
    return this.longUrlCache.get(longUrl,String.class);
  }

  public void clean(String shortUrl) {
      this.shortUrlCache.evict(shortUrl);
  }

}
