package com.domain.urlshortener.core.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigInteger;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 1:08
 */
public class Base62Test {

    @Test
    public void testEncode_1() {
        Assertions.assertEquals("C", Base62.encode(12L));
    }

    @Test
    public void testEncode_2() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Base62.encode(new BigInteger("-1"));
            }
        });
    }

    @Test
    public void testEncode_3() {
        Assertions.assertEquals("0", Base62.encode(0L));
    }

    @Test
    public void testDecode_1() {
        Assertions.assertEquals(12L, Base62.decode("C").longValue());
    }

    @Test
    public void testDecode_2() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Base62.decode("");
            }
        });
    }

    @Test
    public void testBase62() {
        new Base62();
    }

}
