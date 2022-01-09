package interview.assignments.zhanggang.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "short-code")
@Getter
@Setter
public class ShortCodeProperties {
    private int maxLength;
}
