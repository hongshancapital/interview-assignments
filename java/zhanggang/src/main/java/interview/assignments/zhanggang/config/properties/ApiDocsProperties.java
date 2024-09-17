package interview.assignments.zhanggang.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api-docs")
@Getter
@Setter
public class ApiDocsProperties {
    private String title;
    private String version;
    private Developer developer;

    @Getter
    @Setter
    public static class Developer {
        private String name;
        private String email;
    }
}