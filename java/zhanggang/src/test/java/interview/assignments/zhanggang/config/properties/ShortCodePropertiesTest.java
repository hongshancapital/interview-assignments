package interview.assignments.zhanggang.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = ShortCodeProperties.class)
class ShortCodePropertiesTest {

    @Autowired
    private ShortCodeProperties shortCodeProperties;

    @Test
    void getMaxLength() {
        assertThat(shortCodeProperties.getMaxLength()).isEqualTo(10);
    }
}