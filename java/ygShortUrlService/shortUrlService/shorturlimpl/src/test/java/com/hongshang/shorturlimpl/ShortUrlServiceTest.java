package com.hongshang.shorturlimpl;

import com.hongshang.shorturlmodel.api.IUrlDao;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JMockit.class)
public class ShortUrlServiceTest {

    public static final String LONG_URL = "https://mp.weixin.qq.com/s?__biz=MzI3MTEwODc5Ng==&mid=2650860717&idx=1&sn=d41494d71f3ef2298d02000590bda6cb&chksm=f132937ec6451a68832461cd25bc35c03bd23c17b45b3ea622bf960653a006a5e5d0f68d1396&token=1991831939&lang=zh_CN#rd";
    public static final String SHORT_URL = "adzwz2e4";

    @Tested
    private ShortUrlService shortUrlService;

    @Injectable
    private IUrlDao urlDao;

    /**
     * 没有保存过长域名，重新产生对应的短域名，并保存成功的情况
     */
    @Test
    public void testTransformToShortUrl() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        new Expectations() {
            {
                urlDao.getByKey(anyString);
                result = null;
                times =2;
            }
            {
                urlDao.getShortUrl();
                result = shortUrl;
            }
            {
                urlDao.putKeyValue(anyString, anyString);
                result = true;
                times =2;
            }
        };
        String resultShortUrl = shortUrlService.transformToShortUrl(longUrl);
        String expectShortUrl = shortUrl;
        assertEquals(expectShortUrl, resultShortUrl);

    }

    /**
     * 保存过长域名，直接获得到短域名的情况
     */
    @Test
    public void testTransformToShortUrlNull() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        new Expectations() {
            {
                urlDao.getByKey(anyString);
                result = shortUrl;
            }
        };
        String resultShortUrl = shortUrlService.transformToShortUrl(longUrl);
        String expectShortUrl = shortUrl;
        assertEquals(expectShortUrl, resultShortUrl);

    }


    /**
     * 测试没有保存过长域名，重新产生了短域名，便短域名重复，重新多次获取并成功的情况
     */
    @Test
    public void testTransformToShortUrl3() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        String shortUrl2 = SHORT_URL+"2";
        new Expectations() {
            {
                urlDao.getByKey(anyString);
                result = null;
                times =2;
            }
            {
                urlDao.getShortUrl();
                result = shortUrl2;
            }
            {
                urlDao.putKeyValue(anyString, anyString);
                result = false;
            }
            {
                urlDao.getShortUrl();
                result = shortUrl;
            }
            {
                urlDao.putKeyValue(anyString, anyString);
                result = true;
                times =2;
            }
        };
        String resultShortUrl = shortUrlService.transformToShortUrl(longUrl);
        String expectShortUrl = shortUrl;
        assertEquals(expectShortUrl, resultShortUrl);

    }

    /**
     * 测试第一次没有获取到短域名，第二次获取到短域名的，多线程下的情况
     */
    @Test
    public void testTransformToShortUrl4() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        String shortUrl2 = SHORT_URL+"2";
        new Expectations() {
            {
                urlDao.getByKey(anyString);
                result = null;
            }
            {
                urlDao.getByKey(anyString);
                result = shortUrl;
            }
        };
        String resultShortUrl = shortUrlService.transformToShortUrl(longUrl);
        String expectShortUrl = shortUrl;
        assertEquals(expectShortUrl, resultShortUrl);
    }

    /**
     * 通过短域名获取长域名的情况
     */
    @Test
    public void testGetLongUrlByShortUrl() {
        String longUrl = LONG_URL;
        String shortUrl = SHORT_URL;
        new Expectations() {
            {
                urlDao.getByKey(anyString);
                result = longUrl;
            }
        };
        String resultLongUrl = shortUrlService.getLongUrlByShortUrl(shortUrl);
        String expectLongUrl = longUrl;
        assertEquals(expectLongUrl, resultLongUrl);
    }
}