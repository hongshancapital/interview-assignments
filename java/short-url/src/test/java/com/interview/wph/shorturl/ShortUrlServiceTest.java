package com.interview.wph.shorturl;

import com.interview.wph.shorturl.common.utils.EncryptionUtil;
import com.interview.wph.shorturl.service.impl.ShortUrlServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wangpenghao
 * @date 2022/4/24 15:09
 * service 测试类
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortUrlServiceTest {

    @Autowired
    private ShortUrlServiceImpl shortUrlService;
    private static final String longUrl = "http://baidu.com";
    private static String shortUrl = null;

    @Test
    @DisplayName("测试获取短链接")
    @Order(1)
    public void getShortUrl() {
        shortUrl = shortUrlService.postLongUrl(longUrl);
        //判断是否合法
        assertTrue(shortUrl.length() <= 8);
    }

    @Test
    @DisplayName("测试相同长连接多次短链接")
    @Order(2)
    public void getShortUrl2() {
        String s = shortUrlService.postLongUrl(longUrl);
        //判断是否合法
        assertEquals(s, shortUrl);
    }

    @Test
    @DisplayName("测试获取长链接")
    @Order(3)
    public void getLongUrl() {
        assertEquals(longUrl, shortUrlService.getLongUrl(EncryptionUtil.decimalConvertToNumber(shortUrl)));
    }

    @Test
    @DisplayName("测试获取无效长链接1")
    @Order(4)
    public void getInvalidLongUrl1() {
        assertEquals(null, shortUrlService.getLongUrl(EncryptionUtil.decimalConvertToNumber("test")));
    }

    @Test
    @DisplayName("测试获取无效长链接2")
    @Order(5)
    public void getInvalidLongUrl2() {
        assertEquals(null, shortUrlService.getLongUrl(EncryptionUtil.decimalConvertToNumber("test")));
    }

}
