package com.domain.url.helper;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringHelperTest {

    @Test
    public void isUrl() {
        Assertions.assertTrue(StringHelper.isUrl("https://www.baidu.com"));
        Assertions.assertFalse(StringHelper.isUrl(null));
    }
}