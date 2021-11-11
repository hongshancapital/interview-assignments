package com.lynnhom.sctdurlshortservice.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description: Base62Util测试类
 * @author: Lynnhom
 * @create: 2021-11-09 23:28
 **/

public class Base62UtilTest {

    @Test
    void testEncode() {
        Assertions.assertThrows(RuntimeException.class, () -> Base62Util.encode(null));
        Assertions.assertThrows(RuntimeException.class, () -> Base62Util.encode(-1L));
        Assertions.assertEquals("M", Base62Util.encode(0L));
        Assertions.assertEquals("c", Base62Util.encode(61L));
        Assertions.assertEquals("hMM", Base62Util.encode(62L * 62L));
        Assertions.assertEquals("cccc", Base62Util.encode(62L * 62L * 62L * 62L -1));
    }

    @Test
    void testDecode() {
        Assertions.assertThrows(RuntimeException.class, () -> Base62Util.decode("null!~"));
        Assertions.assertEquals(0L, Base62Util.decode("M"));
        Assertions.assertEquals(61L, Base62Util.decode("c"));
        Assertions.assertEquals(62L * 62L, Base62Util.decode("hMM"));
        Assertions.assertEquals(62L * 62L * 62L * 62L -1, Base62Util.decode("cccc"));
    }
}
