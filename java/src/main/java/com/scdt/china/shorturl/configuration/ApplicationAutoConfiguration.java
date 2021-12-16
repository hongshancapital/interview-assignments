package com.scdt.china.shorturl.configuration;

import com.scdt.china.shorturl.repository.id.IdGenerator;
import com.scdt.china.shorturl.repository.id.IncrementIdGenerator;
import com.scdt.china.shorturl.repository.id.RandomIdGenerator;
import com.scdt.china.shorturl.repository.id.SnowflakeIdGenerator;
import com.scdt.china.shorturl.utils.NumberCodecUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.NoSuchAlgorithmException;

/**
 * 短地址应用自动配置
 *
 * @author ng-life
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationAutoConfiguration {

    private final ApplicationProperties applicationProperties;

    public ApplicationAutoConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    @Profile("snowflake")
    public IdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(applicationProperties.getDatacenterId(), applicationProperties.getWorkerId(), applicationProperties.getBaseTime());
    }

    @Bean
    @Profile("increment")
    public IdGenerator incrementIdGenerator() {
        return new IncrementIdGenerator(0, NumberCodecUtils.decode("zzzzzzzz"));
    }

    @Bean
    @Profile("random")
    public IdGenerator randomIdGenerator() throws NoSuchAlgorithmException {
        return new RandomIdGenerator(0, NumberCodecUtils.decode("zzzzzzzz"));
    }

}
