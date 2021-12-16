package com.scdt.china.shorturl.repository.id;

import com.scdt.china.shorturl.utils.NumberCodecUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

class RandomIdGeneratorTest {

    private RandomIdGenerator randomIdGenerator;
    private Long maxValue;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        maxValue = NumberCodecUtils.decode("zzzzzzzz");
        randomIdGenerator = new RandomIdGenerator(0, maxValue);
    }

    @Test
    void nextId() {

        for (int i = 0; i < 1000; i++) {
            Long result = randomIdGenerator.nextId();
            Assertions.assertTrue(result >= 0);
            Assertions.assertTrue(result < maxValue);
            String code = NumberCodecUtils.encode(result);
            Assertions.assertTrue(code.length() <= 8);
        }
    }

    @Test
    void isRandom() {
        Assertions.assertTrue(randomIdGenerator.isRandom());
    }
}