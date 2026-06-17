package interview.assignments.zhanggang.core.shortid.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShortIdTest {

    @Test
    void test_short_id_value() {
        final long seed = 115_000_000;
        final ShortId shortId = new ShortId(seed);

        assertThat(shortId.getSeed()).isEqualTo(seed);
        assertThat(shortId.getValue()).isEqualTo("hWGUS");
    }
}