package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.config.ShortConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class IntegerGeneratorTest {

    @Test
    void generate() {
        IntegerGenerator generator = new IntegerGenerator(8);
        generator.setShortConfiguration(shortConfiguration);
        assertEquals("0", generator.generate());

        generator = new IntegerGenerator(2);
        generator.setShortConfiguration(shortConfiguration);
        for (int i = 0; i < Character.MAX_RADIX; i++) {
            generator.generate();
        }
        assertEquals("10", generator.generate());

        final IntegerGenerator generator1 = new IntegerGenerator(1);
        generator1.setShortConfiguration(shortConfiguration);
        for (int i = 0; i < Character.MAX_RADIX; i++) {
            generator1.generate();
        }
        assertNull(generator1.generate());
    }

    @Autowired
    ShortConfiguration shortConfiguration;

    @Test
    void validate() {
        IntegerGenerator generator = new IntegerGenerator(shortConfiguration.getUrlMaxLen());
        generator.setShortConfiguration(shortConfiguration);
        assertTrue(generator.validate(shortConfiguration.getShortUrlDomain() + "aaa"));
        assertFalse(generator.validate(shortConfiguration.getShortUrlDomain() + ":ads"));
        assertFalse(generator.validate(null));
        assertFalse(generator.validate("66"));
    }
}