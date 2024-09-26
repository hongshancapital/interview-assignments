package org.example.shorturl.config;

import org.example.shorturl.generator.IdGeneratorWrapper;
import org.example.shorturl.properties.IdGeneratorProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 自动配置引导类
 *
 * @author bai
 * @date 2021/6/19 0:45
 */
@Configuration
@EnableConfigurationProperties({IdGeneratorProperty.class})
public class IdGeneratorConfig {
    @Resource
    private IdGeneratorProperty idGeneratorProperty;
    
    /**
     * id生成器包装器
     *
     * @return {@link IdGeneratorWrapper}
     */
    @Bean
    public IdGeneratorWrapper idGeneratorWrapper() {
        return new IdGeneratorWrapper(idGeneratorProperty);
    }
}
