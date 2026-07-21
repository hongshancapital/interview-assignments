package com.zoujing.shortlink.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HexTransformatUtilTest {
    @Test
    public void testHexTransformatUtil() {
        assertNotNull(HexTransformatUtil.hex10ToAnly("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 55, 8));
        assertNotNull(HexTransformatUtil.hexAnlyTo10("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "AAAAAACD"));
        assertEquals(0, HexTransformatUtil.hexAnlyTo10("ABCDEFGHIJKLMNOPQRSTUVWXYZ", ""));
        assertEquals(55
                , HexTransformatUtil.hexAnlyTo10("ABCDEFGHIJKLMNOPQRSTUVWXYZ", HexTransformatUtil.hex10ToAnly("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 55, 8)));
    }
}
