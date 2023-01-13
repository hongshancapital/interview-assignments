package com.example.tinyurl.impl;

import com.example.tinyurl.dao.UrlDao;
import com.example.tinyurl.service.TinyUrlGenerator;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;

/**
 * Tiny Url REST Controller
 * @author hermitriver@hotmail.com
 */
@SpringBootTest
public class SimpleTinyUrlGeneratorTests {
    private final static String TARGET_URL = "demo";

    @MockBean
    UrlDao urlDao;

    /** Test to generate tiny url from the first 3845 urls */
    @Test
    public void testGenerator() {
        TinyUrlGenerator generator = new SimpleTinyUrlGenerator();
        for (long cur = 0; cur <= 3844; cur++) {
            String url = generator.generate(TARGET_URL);
            if (cur == 0) {
                Assertions.assertEquals(url, "0");
            } else if (cur == 9) {
                Assertions.assertEquals(url, "9");
            } else if (cur == 10) {
                Assertions.assertEquals(url, "A");
            } else if (cur == 35) {
                Assertions.assertEquals(url, "Z");
            } else if (cur == 36) {
                Assertions.assertEquals(url, "a");
            } else if (cur == 61) {
                Assertions.assertEquals(url, "z");
            } else if (cur == 62) {
                Assertions.assertEquals(url, "10");
            } else if (cur == 124) {
                Assertions.assertEquals(url, "20");
            } else if (cur == 3843) {
                Assertions.assertEquals(url, "zz");
            } else if (cur == 3844) {
                Assertions.assertEquals(url, "100");
            }
        }
    }

    /** Test AfterPropertiesSet */
    @Test
    public void testAfterPropertiesSet() {
        SimpleTinyUrlGenerator generator = new SimpleTinyUrlGenerator();

        // null urlDao
        generator.urlDao = null;
        try {
            generator.afterPropertiesSet();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        Assertions.assertEquals("0", generator.generate(TARGET_URL));

        // Exceed the maximum
        generator.urlDao = this.urlDao;
        given(this.urlDao.getMaxId()).willReturn(SimpleTinyUrlHelper.MAX + 1);
        try {
            generator.afterPropertiesSet();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        Assertions.assertNull(generator.generate(TARGET_URL));
    }
}
