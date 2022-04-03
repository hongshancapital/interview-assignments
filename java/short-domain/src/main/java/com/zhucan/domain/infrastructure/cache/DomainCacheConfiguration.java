package com.zhucan.domain.infrastructure.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 23:25
 */
@Configuration
public class DomainCacheConfiguration {

  @Bean
  @ConditionalOnMissingBean(DomainCache.class)
  public DomainCache domainCache(){
    return new LocalCache();
  }
}
