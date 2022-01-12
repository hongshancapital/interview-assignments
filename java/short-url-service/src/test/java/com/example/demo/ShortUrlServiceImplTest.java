package com.example.demo;



import com.example.demo.service.IShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShortUrlServiceImplTest {

    @Autowired
    private IShortUrlService iShortUrlService;

    private static final String testUrl = "https://www.taobao.com.cn";

    @Test
    public void shortUrlConvertLongUrl() {
        // 保存短链接
        String shortUrl = iShortUrlService.longUrlConvertShortUrl(testUrl);
        String longUrl = iShortUrlService.shortUrlConvertLongUrl(shortUrl);
        assertEquals(testUrl, longUrl);
    }

    @Test
    public void longUrlConvertShortUrl() {
        // 第一次保存短链接
        String shortUrl = iShortUrlService.longUrlConvertShortUrl(testUrl);

        // 再次保存相同的长链接
        String shortUrl2 = iShortUrlService.longUrlConvertShortUrl(testUrl);
        // 两次短链接应该相同
        assertEquals(shortUrl, shortUrl2);
    }

}
