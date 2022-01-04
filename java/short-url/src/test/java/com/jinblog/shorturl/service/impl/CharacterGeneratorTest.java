package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.config.ShortConfiguration;
import org.junit.jupiter.api.BeforeEach;
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
class CharacterGeneratorTest {

    String firstWord;
    String secondWord;

    @Autowired
    ShortConfiguration shortConfiguration;

    CharacterGenerator generator;

    @BeforeEach
    public void beforeEach() {
        generator = new CharacterGenerator(shortConfiguration.getUrlMaxLen());
        generator.setShortConfiguration(shortConfiguration);
        if (firstWord == null) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < shortConfiguration.getUrlMaxLen(); i++) {
                str.append(CharacterGenerator.CHARS[0]);
            }
            firstWord = str.toString();

            str.delete(0, str.length());
            for (int i = 0; i < shortConfiguration.getUrlMaxLen() - 1; i++) {
                str.append(CharacterGenerator.CHARS[0]);
            }
            str.append(CharacterGenerator.CHARS[1]);
            secondWord = str.toString();
        }
    }

    @Test
    void generate() {
        // 测试普通自增
        assertEquals(firstWord, generator.generate());
        assertEquals(secondWord, generator.generate());
        // 测试进位
        generator = new CharacterGenerator(shortConfiguration.getUrlMaxLen());

        String str = null;
        StringBuilder stringBuilder = new StringBuilder(firstWord);
        stringBuilder.setCharAt(stringBuilder.length() - 2, CharacterGenerator.CHARS[1]);
        for (int i = 0; i <= CharacterGenerator.CHARS.length; i++) {
            str = generator.generate();
        }
        assertEquals(stringBuilder.toString(), str);

        // 测试到达最大值
        generator = new CharacterGenerator(1);

        generator.setShortConfiguration(shortConfiguration);
        for (int i = 0; i < CharacterGenerator.CHARS.length; i++) {
            generator.generate();
        }
        assertNull(generator.generate());
    }

    @Test
    void validate() {
        CharacterGenerator generator = new CharacterGenerator(shortConfiguration.getUrlMaxLen());
        generator.setShortConfiguration(shortConfiguration);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.shortConfiguration.getUrlMaxLen(); i++) {
            stringBuilder.append(CharacterGenerator.CHARS[0]);
        }
        assertTrue(generator.validate(this.shortConfiguration.getShortUrlDomain() + stringBuilder.toString()));
        assertFalse(generator.validate(this.shortConfiguration.getShortUrlDomain() + "ads"));
    }
}