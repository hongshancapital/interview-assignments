package com.github.shwas1.shorturl.service.generator.snowflake;

import com.github.shwas1.shorturl.ShortUrlApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SnowflakeShortPathGeneratorTest extends ShortUrlApplicationTests {

    @Autowired
    private SnowflakeShortPathGenerator snowflakeShortPathGenerator;

    @Test
    void generate() {
        Set<String> set = new HashSet<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            String shortPath = snowflakeShortPathGenerator.generate();

            assertNotNull(shortPath);
            assertEquals(8, shortPath.length());
            set.add(shortPath);
        }
        assertEquals(set.size(), size);
    }
}