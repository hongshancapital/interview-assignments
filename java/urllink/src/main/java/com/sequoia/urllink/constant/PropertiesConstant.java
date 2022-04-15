package com.sequoia.urllink.constant;

/**
 * application.properties配置文件中的key
 * @author liuhai
 * @date 2022.4.15
 */
public interface PropertiesConstant {
  /**
   * 账户信息
   */
  String JWT_URL_THIRD_USERS = "jwt.url.third.users";
  /**
   * kafka
   */
  String BOOT_SERVERS = "bootstrap.servers";
  String COMMIT_MS = "auto.commit.interval.ms";
  String GROUP_ID = "group.id";
  String KEY_DESER = "key.deserializer";
  String VALUE_DESER = "value.deserializer";
  /**
   * 数据源常量
   */
  String JDBC_DRIVERCLASS = "spring.datasource.driverClassName";
  String JDBC_EVICTIONRUNSMILLIS = "spring.datasource.time-between-eviction-runs-millis";
  String JDBC_INITIALSIZE = "spring.datasource.initialSize";
  String JDBC_INTERCEPTORS = "spring.datasource.jdbcInterceptors";
  String JDBC_MAXACTIVE = "spring.datasource.maxActive";
  String JDBC_MAXIDLE = "spring.datasource.maxIdle";
  String JDBC_MAXWAIT = "spring.datasource.maxWait";
  String JDBC_MINIDLE = "spring.datasource.minIdle";
  String JDBC_PASSWORD = "spring.datasource.password";
  String JDBC_REMOVE = "spring.datasource.removeAbandoned";
  String JDBC_TESTONBORROW = "spring.datasource.test-on-borrow";
  String JDBC_TESTWHILEIDLE = "spring.datasource.test-while-idle";
  String JDBC_TIMEOUT = "spring.datasource.removeAbandonedTimeout";
  String JDBC_URL = "spring.datasource.url";
  String JDBC_USERNAME = "spring.datasource.username";
  /**
   * redis MQ
   */
  String REDIS_MQ_CHANNELS = "redis.mq.channels";
  String REDIS_MQ_CLASSNAME = ".className";
  String REDIS_MQ_METHOD = "receiveMessage";
  String REDIS_MQ_PRE = "redis.mq.";
}
