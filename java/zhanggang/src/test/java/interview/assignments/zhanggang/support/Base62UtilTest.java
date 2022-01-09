package interview.assignments.zhanggang.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Base62UtilTest {

    @Test
    void test_base10_convert_to_base64() {
        final String base62 = Base62Util.base10to62(211_111_211_112_111L);
        assertThat(base62).isEqualTo("76Tu7Est");
    }

    @Test
    void test_base62_convert_to_base10() {
        final long base10 = Base62Util.base62to10("rLHWfKd");
        assertThat(base10).isEqualTo(999_999_999_999L);
    }
}