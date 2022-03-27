package org.demo.shortlink.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wsq
 * @date 2022/3/26 002618:38
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class NumberUtilTest {

    @Test
    void encode10To62() {
        assertNotNull(NumberUtil.encode10To62(System.currentTimeMillis()));
        assertNotNull(NumberUtil.encode10To62(-1));
    }

    @Test
    void testEncode10To62() {
        assertNotNull(NumberUtil.encode10To62(System.currentTimeMillis(), 4));
        assertNotNull(NumberUtil.encode10To62(-1, 4));
    }

    @Test
    void decode62To10() {
        NumberUtil.decode62To10("xnjekge");
        NumberUtil.decode62To10(null);
        NumberUtil.decode62To10("@#$fgdg33");
    }
}