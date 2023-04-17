package com.example.shorturl.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@SpringBootTest
class Base62UtilTest {

    @Test
    void test() {
        long base10 = 1000000L;
        final String base62 = Base62Util.base10ToBase62(base10);
        final long base10After = Base62Util.base62ToBase10(base62);
        Assert.isTrue(base10 == base10After, "base10After not match");

        final String s = Base62Util.base10ToBase62(0);
        Assert.isTrue("a".equals(s), "0 is not a");
    }
}