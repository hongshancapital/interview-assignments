package interview.assignments.zhanggang.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = ShortenerConfig.class)
class ShortenerConfigTest {

    @Autowired
    private ShortenerConfig shortenerConfig;

    @Test
    void test_shortener_config() {
        assertThat(shortenerConfig.getMaxLength()).isEqualTo(10);
        assertThat(shortenerConfig.getShortUrlHost()).isEqualTo("https://shortener.com");
        assertThat(shortenerConfig.getLockConfig().getTimeout()).isEqualTo(10);
        assertThat(shortenerConfig.getLockConfig().getTimeunit()).isEqualTo(TimeUnit.MILLISECONDS);
    }
}