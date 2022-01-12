package interview.assignments.zhanggang.core.shortener.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ShortenerTest {

    @Test
    void test_create_shortener() {
        final Shortener shortener = new Shortener("id", "url");

        assertThat(shortener.getId()).isEqualTo("id");
        assertThat(shortener.getOriginalUrl()).isEqualTo("url");
        assertThat(shortener.getCreateAt()).isBeforeOrEqualTo(Instant.now());
    }
}