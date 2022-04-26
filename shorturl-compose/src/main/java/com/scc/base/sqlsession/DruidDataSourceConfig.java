package com.scc.base.sqlsession;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author renyunyi
 * @date 2022/4/25 3:51 PM
 * @description druid datasource
 **/
@Configuration
public class DruidDataSourceConfig {

    @Value("${druid.datasource.initialSize}")
    private int initialSize;

    @Value("${druid.datasource.maxActive}")
    private int maxActive;

    @Value("${druid.datasource.maxWait}")
    private int maxWait;

    @Value("${druid.datasource.minIdle}")
    private int minIdle;

    @Value("${druid.datasource.keepAlive}")
    private boolean keepAlive;

    @Value("${druid.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${druid.datasource.driver}")
    private String driver;

    @Value("${druid.datasource.url}")
    private String url;

    @Value("${druid.datasource.username}")
    private String username;

    @Value("${druid.datasource.password}")
    private String password;

    @Bean("druidDatasource")
    public DruidDataSource druidDatasource() throws SQLException {

        DruidDataSource dataSource = new DruidDataSource(true);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setKeepAlive(keepAlive);

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.init();
        return dataSource;
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("druidDatasource") DataSource druidDatasource) {
        final DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(druidDatasource);
        return txManager;
    }
}
