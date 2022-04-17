package com.yilong.shorturl.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class Number62Test {

    @Test
    public void testEncodeNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Number62.encode(-1);
            }
        });
    }

    @Test
    public void testEncode1() {
        Assertions.assertEquals("1Evr03", Number62.encode(1136789967));
    }

    @Test
    public void testEncode2() {
        Assertions.assertEquals("z", Number62.encode(61));
    }

    @Test
    public void testEncode3() {
        Assertions.assertEquals("10", Number62.encode(62));
    }

    @Test
    public void testEncode4() {
        Assertions.assertEquals("0", Number62.encode(0));
    }

    @Test
    public void testDecodeNull() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Number62.decode(null);
            }
        });
    }

    @Test
    public void testDecodeEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Number62.decode("");
            }
        });
    }

    @Test
    public void testDecodeInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Number62.decode("*");
            }
        });
    }

    @Test
    public void testDecode() {
        Assertions.assertEquals(1136789967, Number62.decode("1Evr03"));
    }
}
