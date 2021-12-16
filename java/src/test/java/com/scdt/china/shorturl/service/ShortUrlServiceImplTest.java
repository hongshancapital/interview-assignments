package com.scdt.china.shorturl.service;

import com.scdt.china.shorturl.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShortUrlServiceImplTest {

    @Autowired
    private ShortUrlServiceImpl shortUrlServiceImpl;

    @Test
    public void save() {
        String code1 = shortUrlServiceImpl.generate("https://www.baidu.com");
        String code2 = shortUrlServiceImpl.generate("https://www.google.com");
        Assertions.assertNotEquals(code1, code2);
    }

    @Test
    public void get() {
        String googleUrl = "https://www.google.com";
        String code = shortUrlServiceImpl.generate(googleUrl);
        String fullUrl = shortUrlServiceImpl.expand(code);
        Assertions.assertEquals(googleUrl, fullUrl);
    }


    @Test
    void badCase() {
        Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlServiceImpl.generate("ftp://www.baidu.com");
        });
        Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlServiceImpl.expand("110-222");
        });
        Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlServiceImpl.expand("10086-222");
        });
    }

}