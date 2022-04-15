package com.sequoia.urllink.base.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import static com.sequoia.urllink.constant.PropertiesConstant.*;

/**
 * 使用@Configuration，不能注入RedisConnectionFactory
 */
@Configuration
public class RedisMQConfig {
  private static org.slf4j.Logger logger = LoggerFactory.getLogger(RedisMQConfig.class);
  @Autowired
  private Environment env;

  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);

    String chanelNames = env.getProperty(REDIS_MQ_CHANNELS);
    if (StringUtils.isNotBlank(chanelNames)) {
      String[] chanels = chanelNames.split(",");
      Class className = null;
      MessageListenerAdapter listenerAdapter = null;
      for (String chanel : chanels) {
        try {
          className = Class.forName(env.getProperty(REDIS_MQ_PRE + chanel + REDIS_MQ_CLASSNAME));
          listenerAdapter = new MessageListenerAdapter(className.newInstance(), REDIS_MQ_METHOD);
          listenerAdapter.afterPropertiesSet();
          container.addMessageListener(listenerAdapter, new PatternTopic(chanel));
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
      /**
       * 开启异步消费
       */
      container.afterPropertiesSet();
    }
    return container;
  }
}