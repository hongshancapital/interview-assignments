package interview.assignments.zhanggang.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "short-code")
@Getter
@Setter
public class ShortenerConfig {
    private int maxLength;
    private LockConfig lockConfig;
    private String shortUrlHost;

    @Getter
    @Setter
    public static class LockConfig {
        private long timeout;
        private TimeUnit timeunit;
    }
}