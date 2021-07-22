package com.hello.tinyurl.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TinyUrlServiceImplTest {

    @Resource
    private TinyUrlServiceImpl tinyUrlService;

    @Test
    public void tinyUrlServiceTest() throws Exception {
        String originalUrl = "test";
        String tinyUrl = tinyUrlService.saveOriginalUrl(originalUrl);
        String original = tinyUrlService.getOriginalUrl(tinyUrl);
        assertEquals(original, originalUrl);
    }

    @Test
    public void tinyUrlServiceTestBigMap() throws Exception {
        String tmp = "";
        String tinyUrl = "";
        String original = "";
        for (int i = 0; i < 2000; i++) {
            tmp = getRandomString(16);
            tinyUrl = tinyUrlService.saveOriginalUrl(tmp);
            original = tinyUrlService.getOriginalUrl(tinyUrl);
            assertEquals(tmp, original);
        }
    }

    private String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }

}