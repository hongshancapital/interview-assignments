package interview.assignments.zhanggang.core.shortcode.model;

import interview.assignments.zhanggang.config.exception.ShortCodeMaximumLimitException;
import interview.assignments.zhanggang.config.properties.ShortCodeProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShortCodeTest {

    @Test
    void test_short_code_value_success() {
        final long seed = 115_000_000;
        final ShortCodeProperties properties = new ShortCodeProperties();
        properties.setMaxLength(8);
        final ShortCode shortCode = new ShortCode(seed, properties);

        assertThat(shortCode.getSeed()).isEqualTo(seed);
        assertThat(shortCode.getValue()).isEqualTo("hWGUS");
    }

    @Test
    void test_short_code_value_reached_the_maximum_length_limit() {
        final long seed = 115_000_000;
        final ShortCodeProperties properties = new ShortCodeProperties();
        properties.setMaxLength(3);
        final ShortCode shortCode = new ShortCode(seed, properties);

        assertThatThrownBy(shortCode::getValue)
                .isInstanceOf(ShortCodeMaximumLimitException.class);
    }
}