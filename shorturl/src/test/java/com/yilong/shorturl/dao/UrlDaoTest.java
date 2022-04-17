package com.yilong.shorturl.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlDaoTest {

    @Autowired
    private UrlDao urlDao;

    @Test
    public void testSaveUrl() {
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        String code = urlDao.saveUrl(longUrl);
        Assertions.assertEquals("1Evr03", code);
    }

    @Test
    public void testGetUrlByShortCodeNull() {
        String longUrl = urlDao.getUrlByShortCode("NotExist");
        Assertions.assertEquals(null, longUrl);
    }

    @Test
    public void testGetUrlByShortCode() {
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        String code = urlDao.saveUrl(longUrl);
        String longUrlGet = urlDao.getUrlByShortCode(code);
        Assertions.assertEquals(longUrl, longUrlGet);
    }
}
