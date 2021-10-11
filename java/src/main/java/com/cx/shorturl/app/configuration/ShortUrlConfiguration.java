package com.cx.shorturl.app.configuration;

import com.cx.shorturl.app.id.DefaultIdGenerator;
import com.cx.shorturl.app.id.IdGenerator;
import com.cx.shorturl.app.service.ShortUrlService;
import com.cx.shorturl.app.service.impl.ShortUrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    @ConditionalOnMissingBean(ShortUrlService.class)
    public ShortUrlService tinyUrlConverter(@Autowired IdGenerator idGenerator) {
        return new ShortUrlServiceImpl(idGenerator);
    }
}
