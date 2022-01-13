package interview.assignments.zhanggang.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@EnableConfigurationProperties
@SpringBootTest(classes = ApiDocsProperties.class)
class ApiDocsPropertiesTest {
    @Autowired
    private ApiDocsProperties apiDocsProperties;

    @Test
    void test_api_doc_config() {
        assertThat(apiDocsProperties.getTitle()).isEqualTo("api-docs-title");
        assertThat(apiDocsProperties.getVersion()).isEqualTo("api-version");
        assertThat(apiDocsProperties.getDeveloper().getName()).isEqualTo("dev-name");
        assertThat(apiDocsProperties.getDeveloper().getEmail()).isEqualTo("dev-email");
    }
}