package com.interview.assignment.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.interview.assignment.domain.Application;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 模拟对Application的数据库操作
 */
@Component
public class ApplicationRepository {

  /**
   * appId与Application的映射，模拟数据库有appId字段的索引
   */
  private static final Cache<String, Application> appIdMap = CacheBuilder.newBuilder()
    .maximumSize(100000)
    .build();

  /**
   * 根据app id查询对应的业务线配置数据
   *
   * @param appId 业务线id
   * @return 业务线配置
   */
  public Application findByAppId(String appId) {
    if (null == appId || appId.trim().isEmpty()) {
      return null;
    }



    return appIdMap.getIfPresent(appId);
  }

  /**
   * 由于数据是存储在内存中，因而这里模拟存量数据的初始化
   */
  @PostConstruct
  public void init() {
    Application application = new Application();
    application.setId(1L);
    application.setAppId("test");
    application.setName("系统测试");
    application.setCreateTime(new Date());
    application.setUpdateTime(new Date());
    appIdMap.put("test", application);
  }
}
