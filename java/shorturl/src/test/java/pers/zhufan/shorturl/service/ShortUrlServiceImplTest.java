package pers.zhufan.shorturl.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhufan.shorturl.ShortUrlApplication;
import pers.zhufan.shorturl.config.ShortUrlConfig;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class)
public class ShortUrlServiceImplTest {

    @Resource
    ShortUrlService shortUrlService;

    @Resource
    ShortUrlConfig shortUrlConfig;

    @Test
    public void generalShortUrl() {

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        ShorterUrl shorterUrl = shortUrlService.generalShortUrl(shortUrlConfig, longUrl);

        System.out.println(shorterUrl);

    }

    @Test
    public void recoverLongUrl() {

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        ShorterUrl shorterUrl = shortUrlService.generalShortUrl(shortUrlConfig, longUrl);

        String s = shortUrlService.recoverLongUrl(shorterUrl.getShorter());

        Assert.assertEquals(longUrl,s);

    }

    @Test
    public void recoverLongUrlNull() {

        String longUrl = "http://www.baidu.cam/api/test/query/09846754834455";

        ShorterUrl shorterUrl = shortUrlService.generalShortUrl(shortUrlConfig, longUrl);

        String s = shortUrlService.recoverLongUrl(shorterUrl.getShorter()+"123456678");

        Assert.assertEquals(null,s);

    }

}