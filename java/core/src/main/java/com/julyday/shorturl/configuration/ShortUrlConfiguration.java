package com.julyday.shorturl.configuration;

import com.julyday.shorturl.service.IdGenerator;
import com.julyday.shorturl.service.impl.DefaultIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShortUrlConfiguration {

    @Bean
    @ConditionalOnMissingBean(IdGenerator.class)
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }
}
