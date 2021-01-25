package com.luman.shorturl.cache;

import com.luman.shorturl.api.cache.ShortUrlCache;
import com.luman.shorturl.api.model.ShortUrl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShortUrlCacheTest {
    @Autowired
    ShortUrlCache cache;
    @Test
    public void testSave(){
        ShortUrl shortUrl  = new ShortUrl();
        shortUrl.setCreateTime(new Date());
        shortUrl.setCode("test");
        shortUrl.setUrl("https://www.baidu.com");
        shortUrl.setId(1000000000L);
        cache.save(shortUrl);
        ShortUrl idShortUrl = cache.getUrl(1L);
        TestCase.assertNotNull(idShortUrl);
        TestCase.assertEquals(idShortUrl.getId(),shortUrl.getId());
        ShortUrl codeShortUrl = cache.getUrl("test");
        TestCase.assertNotNull(codeShortUrl);
        TestCase.assertEquals(codeShortUrl.getCode(),"test");
    }

    @Test
    public void testGetUrl(){
        ShortUrl shortUrl  = new ShortUrl();
        shortUrl.setCreateTime(new Date());
        shortUrl.setCode("test");
        shortUrl.setUrl("https://www.baidu.com");
        shortUrl.setId(1L);
        cache.save(shortUrl);

        String code = cache.getShortUrl("https://www.baidu.com",null);
        TestCase.assertNotNull(code);
        TestCase.assertEquals(code,"test");
    }


}
