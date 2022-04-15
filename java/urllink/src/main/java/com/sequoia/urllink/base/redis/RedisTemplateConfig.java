package com.sequoia.urllink.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 对RedisTemplate<Object, Object>的key和value进行序列化设置
 */
@Component
public class RedisTemplateConfig {

  @Autowired
  public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    redisTemplate.setKeySerializer(stringRedisSerializer);
    redisTemplate.setHashKeySerializer(stringRedisSerializer);

    redisTemplate.setValueSerializer(stringRedisSerializer);
    redisTemplate.setHashValueSerializer(stringRedisSerializer);
  }
}