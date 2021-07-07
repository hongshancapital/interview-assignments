package com.shorturl;

import com.shorturl.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ShortUrlServiceTest {

    private ShortUrlService shortUrlService = new ShortUrlService();


    @Test
    public void createShortUrlTest() {
        String shortUrl = shortUrlService.createShortUrl(Data.LONG_URL);
        Assert.assertNotNull(shortUrl);
    }

    @Test
    public void createNullShortUrlTest() {
        String shortUrl = shortUrlService.createShortUrl("");
        Assert.assertNull(shortUrl);
    }

}

