package com.liupf.tiny.url.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liupf.tiny.url.utils.IdCreator;

/**
 * ID生成器配置
 */
@Configuration
public class IdConfig {

    @Bean
    public IdCreator idCreator() {
        return new IdCreator(0);
    }

}
