package com.sequoiacap.domain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

/**
 * 项目集成swagger2自动生成API文档
 * 请求路径为：http://localhost:8063/swagger-ui.html
 */
@SpringBootApplication
@MapperScan(basePackages = "com.sequoiacap.domain.dao",
        sqlSessionFactoryRef = "sqlSessionFactory",
        sqlSessionTemplateRef = "sqlSessionTemplate",annotationClass = Repository.class)
public class DomainNameApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainNameApplication.class, args);
    }

}
