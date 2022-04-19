package com.mortimer.shortenurl.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class Base62Test {
    @Test
    public void test_getInstance() {
        Base62 instance1 = Base62.getInstance();
        Assertions.assertNotNull(instance1);

        Base62 instance2 = Base62.getInstance();
        Assertions.assertEquals(instance1, instance2);
    }

    @Test
    public void test_encode_1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Base62.getInstance().encode(-1));
    }

    @Test
    public void test_encode_2() {
        Base62 instance = Base62.getInstance();
        char[] chs = (char[]) ReflectionTestUtils.getField(instance, "CHARS");
        long num = 0;
        String encoded = instance.encode(num);
        Assertions.assertEquals(String.valueOf(chs[0]), encoded);

        num = 12;
        encoded = instance.encode(num);
        Assertions.assertEquals(String.valueOf(chs[12]), encoded);

        num = 62;
        encoded = instance.encode(num);
        Assertions.assertEquals(new StringBuilder().append(chs[1]).append(chs[0]).toString(), encoded);

        num = 100;
        encoded = instance.encode(num);
        Assertions.assertEquals(new StringBuilder().append(chs[1]).append(chs[38]).toString(), encoded);
    }
}
