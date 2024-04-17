package com.domain.url.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DigitHelperTest {

    @Test
    public void generateHex62() {
        for (int i = 0; i < 10; i++) {
            final String s = DigitHelper.generateHex62();
            Assert.assertTrue(s.length() > 0 && s.length() <= 8);
        }
    }
}