package com.yilong.shorturl.config;

import com.yilong.shorturl.storage.UrlStorage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "url.storage")
@Setter
@Getter
public class UrlStorageConfig {

    private long maximumSize;

    @Bean
    public UrlStorage urlStorage() {
        return new UrlStorage(maximumSize);
    }
}
