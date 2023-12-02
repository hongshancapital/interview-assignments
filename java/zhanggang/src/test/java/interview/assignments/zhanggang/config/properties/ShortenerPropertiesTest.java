package interview.assignments.zhanggang.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@EnableConfigurationProperties
@SpringBootTest(classes = ShortenerProperties.class)
class ShortenerPropertiesTest {

    @Autowired
    private ShortenerProperties shortenerProperties;

    @Test
    void test_shortener_config() {
        assertThat(shortenerProperties.getMaxLength()).isEqualTo(10);
        assertThat(shortenerProperties.getMaxStoreSize()).isEqualTo(2000);
        assertThat(shortenerProperties.getGcRate()).isEqualTo(0.5f);
        assertThat(shortenerProperties.getShortUrlHost()).isEqualTo("https://shortener.com");
        assertThat(shortenerProperties.getLockConfig().getTimeout()).isEqualTo(10);
        assertThat(shortenerProperties.getLockConfig().getTimeunit()).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(shortenerProperties.getLockConfig().getMaxPoolSize()).isEqualTo(15);
    }
}