package demo.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ShortUrlServiceTest {
    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void getShort(){
        String shortUrl = shortUrlService.getShortUrl("http://localhost:8080/swagger-ui.html#/");
        Assert.assertNotNull(shortUrl);
    }

    @Test
    public void getLong(){
        String longUrl = shortUrlService.getLongUrl("50Sin9");
        Assert.assertNotNull(longUrl);
    }
}