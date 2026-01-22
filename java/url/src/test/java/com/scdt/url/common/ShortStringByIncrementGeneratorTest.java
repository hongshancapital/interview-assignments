package com.scdt.url.common;

import com.scdt.url.common.util.ShortStringByIncrementGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ShortStringByIncrementGeneratorTest {

    @Test
    void testIncremental_Dif() {
        String value = "asdasasd.com/1234567";
        IntStream.range(0, 1000).forEach(t -> ShortStringByIncrementGenerator.generate(value, 8));
        var result1 = ShortStringByIncrementGenerator.generate(value, 8);
        var result2 = ShortStringByIncrementGenerator.generate(value, 8);
        assertNotEquals(result1, result2);

    }

}
