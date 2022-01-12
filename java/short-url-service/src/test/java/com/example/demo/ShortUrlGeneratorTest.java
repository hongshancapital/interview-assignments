package com.example.demo;


import com.example.demo.model.utils.ShortUrlGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShortUrlGeneratorTest {

    @Test
    public void longUrlToShortUrl() {
        String baidu = ShortUrlGenerator.generate("www.baidu.com");
        assertEquals("EjiLP3", baidu);

        String taobao = ShortUrlGenerator.generate("www.taobao.com");
        assertEquals("Eb2vKK", taobao);
    }


}
