package com.zhucan.domain.infrastructure.configuration;

import com.zhucan.domain.infrastructure.cache.DomainCacheConfiguration;
import com.zhucan.domain.infrastructure.cache.LocalCache;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 19:01
 */
public class DomainCacheConfigurationTests {

  @Test
  public void cacheConfigTest() {
    assertThat(new DomainCacheConfiguration().domainCache() instanceof LocalCache, equalTo(Boolean.TRUE));
  }
}
