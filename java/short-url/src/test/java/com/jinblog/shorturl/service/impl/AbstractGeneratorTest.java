package com.jinblog.shorturl.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class AbstractGeneratorTest {

    public AbstractGenerator generator;
    @BeforeEach
    public void beforeEach() {
        if (generator == null) {
            generator = new CharacterGenerator(8);
        }
    }

    @Test
    void getUrlMaxLen() {
        generator.setUrlMaxLen(9);
        assertEquals(9, generator.getUrlMaxLen());
    }

    @Test
    void setUrlMaxLen() {
        generator.setUrlMaxLen(10);
        assertEquals(10, generator.getUrlMaxLen());
    }
}