package interview.assignments.zhanggang.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Base62UtilTest {

    @Test
    void test_base10_convert_to_base64() {
        final String base62 = Base62Util.base10to62(211_111_211_112_111L);
        assertThat(base62).isEqualTo("76Tu7Est");

        final long base10 = base62to10(base62);
        assertThat(base10).isEqualTo(211_111_211_112_111L);

    }

    private long base62to10(final String base62) {
        long value = 0;
        for (int i = 0; i < base62.length(); i++) {
            if ('a' <= base62.charAt(i) && base62.charAt(i) <= 'z')
                value = value * 62 + base62.charAt(i) - 'a';
            if ('A' <= base62.charAt(i) && base62.charAt(i) <= 'Z')
                value = value * 62 + base62.charAt(i) - 'A' + 26;
            if ('0' <= base62.charAt(i) && base62.charAt(i) <= '9')
                value = value * 62 + base62.charAt(i) - '0' + 52;
        }
        return value;
    }
}