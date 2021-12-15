package com.wenchao.jacoco.demo.config;

import com.wenchao.jacoco.demo.storage.IStorage;
import com.wenchao.jacoco.demo.storage.InMemoryStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 储存配置
 * 可以实现Redis存储方案，建议使用ZSet 数据结构，score 对应短链接 通过value获取score 同时通过score获取 value
 * 此处使用 内存双向map实现
 *
 * @author Wenchao Gong
 * @date 2021-12-15
 */
@Configuration
public class StorageConfig {

    @Bean
    public IStorage inMemoryStorage() {
        return new InMemoryStorage();
    }
}
