package interview.assignments.zhanggang.core.shortener.model;

import interview.assignments.zhanggang.config.exception.error.URLFormatException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShortenerTest {
    @Test
    void test_create_shortener() {
        final Shortener shortener = new Shortener("id", "url");

        assertThat(shortener.getId()).isEqualTo("id");
        assertThat(shortener.getOriginalUrl()).isEqualTo("url");
    }

    @Test
    void test_parse_id_by_valid_url() {
        final String id = Shortener.parseId("https://test.com/s1");
        assertThat(id).isEqualTo("s1");
    }

    @Test
    void test_parse_id_by_invalid_url() {
        assertThatThrownBy(() -> Shortener.parseId("123"))
                .isInstanceOf(URLFormatException.class);
    }
}