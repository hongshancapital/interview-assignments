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
  private String userCacheName;

  @Autowired
  private CacheManager cacheManager;
  private Cache cache;

  @PostConstruct
  public void init(){
    this.cache = cacheManager.getCache(userCacheName);
  }

  private Map<String, String> url2ShortUrl = new ConcurrentHashMap<String, String>();
  private Map<String, String> shortUrl2Url = new ConcurrentHashMap<String, String>();

  public void save(String url, String shortUrl) {
    this.cachePut(shortUrl,url);
    this.cachePut(url, shortUrl);
  }

  public String get(String shortUrl) {
    return this.cacheGet(shortUrl,String.class);
  }

  public void clean(String url) {
    String sortUrl = this.cacheGet(url,String.class);
    if (sortUrl != null) {
      this.cacheRemove(url);
      this.cacheRemove(sortUrl);
    }
  }


  protected <T> void cachePut(String key, T value){
    this.cache.put(key,value);
  }

  protected <T> T cacheGet(String key,Class<T> clazz){
    return this.cache.get(key,clazz);
  }

  protected void cacheRemove(String key){
    this.cache.evict(key);
  }

}
