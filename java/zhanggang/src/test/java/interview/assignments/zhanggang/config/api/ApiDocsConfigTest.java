package interview.assignments.zhanggang.config.api;

import interview.assignments.zhanggang.config.properties.ApiDocsProperties;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiDocsConfigTest {

    @Test
    void test_custom_open_api() {
        final ApiDocsProperties apiDocsProperties = mock(ApiDocsProperties.class);
        when(apiDocsProperties.getTitle()).thenReturn("title");
        when(apiDocsProperties.getVersion()).thenReturn("version");
        when(apiDocsProperties.getDeveloper()).thenReturn(mock(ApiDocsProperties.Developer.class));
        when(apiDocsProperties.getDeveloper().getName()).thenReturn("name");
        when(apiDocsProperties.getDeveloper().getEmail()).thenReturn("email");

        final ApiDocsConfig apiDocsConfig = new ApiDocsConfig();
        final OpenAPI openAPI = apiDocsConfig.customOpenAPI(apiDocsProperties);

        assertThat(openAPI.getInfo().getTitle()).isEqualTo("title");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("version");
        assertThat(openAPI.getInfo().getContact().getName()).isEqualTo("name");
        assertThat(openAPI.getInfo().getContact().getEmail()).isEqualTo("email");
    }
}