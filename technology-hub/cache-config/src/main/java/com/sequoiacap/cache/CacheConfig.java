package com.sequoiacap.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CacheConifg
 * @Description: 缓存
 * @Author: xulong.wang
 * @Date 10/10/2021
 * @Version 1.0
 */
@Configuration
@Slf4j
public class CacheConfig{
  @Value("${short.url.timeout:10}")
  private Long shortUrlTimeout;
  @Value("${short.url.userCacheName:shortUrlCache}")
  private String userCacheName;

  /** 缓存项最大数量 */
  private static final long GUAVA_CACHE_SIZE = 100000;

  private RedisCacheManager redisCacheManager(RedisConnectionFactory lettuceConnectionFactory){
    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
    // 设置缓存管理器管理的缓存的默认过期时间
    defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofDays(shortUrlTimeout))
            // 设置 key为string序列化
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            // 设置value为json序列化
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
            // 不缓存空值
            .disableCachingNullValues();

    Set<String> cacheNames = new HashSet<>();
    cacheNames.add(userCacheName);

    // 对每个缓存空间应用不同的配置
    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
    configMap.put(userCacheName, defaultCacheConfig.entryTtl(Duration.ofDays(shortUrlTimeout)));

    RedisCacheManager redisCacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
            .cacheDefaults(defaultCacheConfig)
            .initialCacheNames(cacheNames)
            .withInitialCacheConfigurations(configMap)
            .build();
    return redisCacheManager;
  }

  /*private GuavaCacheManager guavaCacheManager() {
    GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
    guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder()
            .expireAfterWrite(shortUrlTimeout, TimeUnit.DAYS).maximumSize(GUAVA_CACHE_SIZE));
    ArrayList<String> guavaCacheNames = Lists.newArrayList();
    guavaCacheNames.add(userCacheName);
    guavaCacheManager.setCacheNames(guavaCacheNames);
    return guavaCacheManager;
  }*/
  //经对比Caffeine性能较好
  private CaffeineCacheManager caffeineCacheManager() {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(shortUrlTimeout, TimeUnit.DAYS).maximumSize(GUAVA_CACHE_SIZE));
    ArrayList<String> guavaCacheNames = Lists.newArrayList();
    guavaCacheNames.add(userCacheName);
    caffeineCacheManager.setCacheNames(guavaCacheNames);
    return caffeineCacheManager;

  }


  @Bean(name = "cacheManager")
  public CompositeCacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
    RedisCacheManager redisCacheManager = null;
    try{
      lettuceConnectionFactory.getConnection();
      redisCacheManager = redisCacheManager(lettuceConnectionFactory);
    }catch (RuntimeException e){
      log.error("redis链接异常", e);
    }
    CaffeineCacheManager caffeineCacheManager = caffeineCacheManager();
    CompositeCacheManager cacheManager = null;
    if(null == redisCacheManager){
      cacheManager = new CompositeCacheManager(caffeineCacheManager);
    }else {
      cacheManager = new CompositeCacheManager(redisCacheManager, caffeineCacheManager);
    }
    return cacheManager;
  }


}