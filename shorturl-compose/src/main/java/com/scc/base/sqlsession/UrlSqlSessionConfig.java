package com.scc.base.sqlsession;

import com.scc.base.plugin.PrintRunnableSql;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author renyunyi
 * @date 2022/4/25 3:42 PM
 * @description Url sqlSessionConfig
 **/
@Configuration
public class UrlSqlSessionConfig {

    /**
     * mybatis base config
     */
    @Bean
    public static MybatisProperties urlProperties(){
        MybatisProperties mybatisProperties = new MybatisProperties();
        mybatisProperties.setMapperLocations(new String[]{"classpath*:mapper/*.xml"});
        mybatisProperties.setTypeAliasesPackage("com.scc.base.mapper");
        return mybatisProperties;
    }

    /**
     * SqlSessionFactory
     */
    @Bean(name = "urlSqlSessionFactory")
    public static SqlSessionFactory urlSqlSessionFactory(@Qualifier("druidDatasource") DataSource druidDatasource,
                                                         @Qualifier("urlProperties") MybatisProperties urlProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(druidDatasource);
        bean.setPlugins(new PrintRunnableSql());
        bean.setTypeAliasesPackage(urlProperties.getTypeAliasesPackage());
        bean.setTypeHandlersPackage(urlProperties.getTypeHandlersPackage());
        bean.setMapperLocations(urlProperties.resolveMapperLocations());
        return bean.getObject();
    }

    @Bean(name = "mySqlSessionTemplate")
    public static SqlSessionTemplate mySqlSessionTemplate(@Qualifier("urlSqlSessionFactory") SqlSessionFactory urlSqlSessionFactory,
                                                          @Qualifier("urlProperties") MybatisProperties urlProperties) {
        ExecutorType executorType = urlProperties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(urlSqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(urlSqlSessionFactory);
        }
    }

    /**
     * scan basePackage
     */
    @Bean
    public static MapperScannerConfigurer urlMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("mySqlSessionTemplate");
        mapperScannerConfigurer.setBasePackage("com.scc.base.mapper");
        return mapperScannerConfigurer;
    }

}
