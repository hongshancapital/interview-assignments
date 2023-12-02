package interview.assignments.zhanggang.config.api;

import interview.assignments.zhanggang.config.properties.ApiDocsProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocsConfig {
    @Bean
    public OpenAPI customOpenAPI(ApiDocsProperties apiDocsProperties) {
        return new OpenAPI().info(getInfo(apiDocsProperties));
    }

    private Info getInfo(ApiDocsProperties apiDocsProperties) {
        return new Info()
                .title(apiDocsProperties.getTitle())
                .version(apiDocsProperties.getVersion())
                .contact(getContact(apiDocsProperties));
    }

    private Contact getContact(ApiDocsProperties apiDocsProperties) {
        return new Contact()
                .name(apiDocsProperties.getDeveloper().getName())
                .email(apiDocsProperties.getDeveloper().getEmail());
    }
}
