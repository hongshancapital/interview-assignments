package indi.zixiu.shortdomainname.config;

import indi.zixiu.shortdomainname.service.ShortDomainNameGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class AppConfig {
    private int domainNameRepositoryCapacity = 20000000;
    private int shortNameGeneratorDatacenterId = 0;
    private int shortNameGeneratorWorkerId = 0;

    @Bean
    public ShortDomainNameGenerator shortDomainNameGenerator() {
        return new ShortDomainNameGenerator(shortNameGeneratorWorkerId, shortNameGeneratorDatacenterId);
    }

    @Bean("domainNameRepositoryCapacity")
    public int getDomainNameRepositoryCapacity() {
        return domainNameRepositoryCapacity;
    }

    public void setDomainNameRepositoryCapacity(int domainNameRepositoryCapacity) {
        this.domainNameRepositoryCapacity = domainNameRepositoryCapacity;
    }

    public int getShortNameGeneratorDatacenterId() {
        return shortNameGeneratorDatacenterId;
    }

    public void setShortNameGeneratorDatacenterId(int shortNameGeneratorDatacenterId) {
        this.shortNameGeneratorDatacenterId = shortNameGeneratorDatacenterId;
    }

    public int getShortNameGeneratorWorkerId() {
        return shortNameGeneratorWorkerId;
    }

    public void setShortNameGeneratorWorkerId(int shortNameGeneratorWorkerId) {
        this.shortNameGeneratorWorkerId = shortNameGeneratorWorkerId;
    }
}
