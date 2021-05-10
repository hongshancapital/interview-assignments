package com.zs.shorturl.service.impl;

import com.zs.shorturl.ShorturlApplication;
import com.zs.shorturl.cache.LongOrShortUrlCache;
import com.zs.shorturl.enity.vo.Result;
import com.zs.shorturl.service.IShortUrlService;
import com.zs.shorturl.utils.ShortUrlGenerateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class ShortUrlServiceImplTest {

    @Autowired
    IShortUrlService shortUrlService;


    @Before
    public void initData(){

    }


    @Test
    public void getShortUrlFromLongUrl() {

        String urlPre = "https://www.baidu.com/";

        HashSet<String> hashSet = new HashSet<>();

        String firstShortUrl = null;

        for (int i = 0; i < 100; i++) {
            String longUrl = urlPre + i;
            Result shortUrlFromLongUrl = shortUrlService.getShortUrlFromLongUrl(longUrl);
            Assert.assertEquals(true,shortUrlFromLongUrl.isSuccess());
            String data = (String) shortUrlFromLongUrl.getData();
            if (i == 0){
                firstShortUrl = data;
            }
            String substring = data.substring(data.lastIndexOf("/") + 1);
            Assert.assertEquals(8,substring.length());
            hashSet.add(substring);
        }
        Assert.assertEquals(100,hashSet.size());

        Result shortUrlFromLongUrl1 = shortUrlService.getShortUrlFromLongUrl("https://www.baidu.com/0");
        Assert.assertEquals(firstShortUrl,shortUrlFromLongUrl1.getData());

        String url = null;
        Result shortUrlFromLongUrl = shortUrlService.getShortUrlFromLongUrl(url);
        Assert.assertEquals(true,shortUrlFromLongUrl.isFail());

    }

    @Test
    public void getLongUrlFromShortUrl() {

        LongOrShortUrlCache.putShortForKey("http://www.zs.com/W4QQQzcf","https://blog.csdn.net/chengqiuming/article/details/103753549");
        LongOrShortUrlCache.putShortForKey("http://www.zs.com/W4QQWcfd","https://blog.csdn.net/qq_14861089/article/details/110479992");
        LongOrShortUrlCache.putShortForKey("http://www.zs.com/W4QQEdfl","https://blog.csdn.net/qq_22638399/article/details/85600621");


        Result longUrlFromShortUrl = shortUrlService.getLongUrlFromShortUrl("http://www.zs.com/W4QQQzcf");
        Assert.assertEquals("https://blog.csdn.net/chengqiuming/article/details/103753549",longUrlFromShortUrl.getData());
        Result longUrlFromShortUrl1 = shortUrlService.getLongUrlFromShortUrl("http://www.zs.com/W4QQWcfd");
        Assert.assertEquals("https://blog.csdn.net/qq_14861089/article/details/110479992",longUrlFromShortUrl1.getData());
        Result longUrlFromShortUrl2 = shortUrlService.getLongUrlFromShortUrl("http://www.zs.com/W4QQEdfl");
        Assert.assertEquals("https://blog.csdn.net/qq_22638399/article/details/85600621",longUrlFromShortUrl2.getData());

        Result longUrlFromShortUrl3 = shortUrlService.getLongUrlFromShortUrl("http://www.zs.com/W4QQEdfr");
        Assert.assertTrue(longUrlFromShortUrl3.isFail());

        Result longUrlFromShortUrl4 = shortUrlService.getLongUrlFromShortUrl("http://www.baidu.com/W4QQEdfr");
        Assert.assertTrue(longUrlFromShortUrl4.isFail());

        Result longUrlFromShortUrl5 = shortUrlService.getLongUrlFromShortUrl("");
        Assert.assertTrue(longUrlFromShortUrl5.isFail());


    }
}