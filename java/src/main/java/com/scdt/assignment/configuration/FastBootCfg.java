package com.scdt.assignment.configuration;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.xiesx.fastboot.core.body.annotation.GoEnableBody;
import com.xiesx.fastboot.core.exception.annotation.GoEnableException;
import com.xiesx.fastboot.core.fastjson.annotation.GoEnableFastJson;

/**
 * @title FastBootCfg.java
 * @description
 * @author
 * @date 2022-04-15 17:10:27
 */
@Configuration
// 启用统一返回
@GoEnableBody
// 启用全局异常
@GoEnableException
// 启用数据转换
@GoEnableFastJson
public class FastBootCfg {

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
