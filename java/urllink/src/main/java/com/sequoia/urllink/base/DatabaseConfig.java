package com.sequoia.urllink.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.sequoia.urllink.constant.PropertiesConstant.*;

/**
 * 用来切换不同的数据库
 * 数据库的连接池
 * @author liuhai
 * @date 2022.4.15
 */
@Configuration
public class DatabaseConfig {

  @Value("${" + JDBC_DRIVERCLASS + "}")
  private String driverClass;
  @Value("${" + JDBC_INITIALSIZE + "}")
  private int initialSize;
  @Value("${" + JDBC_INTERCEPTORS + "}")
  private String interceptors;
  @Value("${" + JDBC_MAXACTIVE + "}")
  private int maxActive;
  @Value("${" + JDBC_MAXIDLE + "}")
  private int maxIdle;
  @Value("${" + JDBC_MAXWAIT + "}")
  private int maxWait;
  @Value("${" + JDBC_MINIDLE + "}")
  private int minIdle;
  @Value("${" + JDBC_PASSWORD + "}")
  private String password;
  @Value("${" + JDBC_REMOVE + "}")
  private boolean remove;
  @Value("${" + JDBC_TESTONBORROW + "}")
  private boolean testOnBorrow;
  @Value("${" + JDBC_TESTWHILEIDLE + "}")
  private boolean testWhileIdle;
  @Value("${" + JDBC_EVICTIONRUNSMILLIS + "}")
  private int timeBetweenEvictionRunsMillis;
  @Value("${" + JDBC_TIMEOUT + "}")
  private int timeout;
  @Value("${" + JDBC_URL + "}")
  private String url;
  @Value("${" + JDBC_USERNAME + "}")
  private String userName;

  /**
   * 自定义数据源，是可以根据具体情况配置
   *
   * @return DataSource
   */
  @Bean
  public DataSource getDataSource() {
    PoolProperties poolProperties = new PoolProperties();
    poolProperties.setDriverClassName(driverClass);
    poolProperties.setUsername(userName);
    poolProperties.setPassword(password);
    poolProperties.setUrl(url);
    poolProperties.setInitialSize(initialSize);
    poolProperties.setMaxActive(maxActive);
    poolProperties.setMaxIdle(maxIdle);
    poolProperties.setMinIdle(minIdle);
    poolProperties.setMaxWait(maxWait);
    poolProperties.setValidationQuery(DatabaseDriver.fromJdbcUrl(poolProperties.getUrl()).getValidationQuery());
    poolProperties.setRemoveAbandonedTimeout(timeout);
    poolProperties.setRemoveAbandoned(remove);

    if (StringUtils.isNotBlank(interceptors)) {
      poolProperties.setJdbcInterceptors(interceptors);
    }

    poolProperties.setTestOnBorrow(testOnBorrow);
    poolProperties.setTestWhileIdle(testWhileIdle);
    poolProperties.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

    org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
    return dataSource;
  }

}
