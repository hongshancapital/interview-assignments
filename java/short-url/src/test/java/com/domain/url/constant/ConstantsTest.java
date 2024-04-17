package com.domain.url.constant;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConstantsTest {
    @Test
    public void testConstants() {
        Assertions.assertNotNull(Constants.DOMAIN_SHORT);
        Assertions.assertNotNull(Constants.URL_MAP);
    }
}