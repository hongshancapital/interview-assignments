package interview.assignments.zhanggang.core.shortid.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShortIdTest {

    @Test
    void test_short_code_value_success() {
        final long id = 115_000_000;
        final ShortId shortId = new ShortId(id);

        assertThat(shortId.getId()).isEqualTo(id);
    }
}