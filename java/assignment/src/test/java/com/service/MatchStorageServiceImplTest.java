package com.service;

import com.AbstractTest;
import com.TestData;
import com.constant.CommonConstants;
import com.exception.SystemException;
import com.service.impl.MatchStorageServiceImpl;
import com.service.impl.UrlTransformServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;

public class MatchStorageServiceImplTest extends AbstractTest {

    @InjectMocks
    private MatchStorageServiceImpl iMatchStorageServiceImpl;

    @Test
    public void testGetShortUrlCacheSize() {
        int shortUrlCacheSize = iMatchStorageServiceImpl.getShortUrlCacheSize();
    }
    @Test
    public void testGetLongUrlCacheSize() {
        int longUrlCacheSize = iMatchStorageServiceImpl.getLongUrlCacheSize();
    }
    @Test
    public void testGetUrlByCacheSucc() {
        iMatchStorageServiceImpl.setShortLongUrlCache(TestData.LONG_URL,TestData.SHORT_URL);
        String shortUrl = iMatchStorageServiceImpl.getShortUrlCache(TestData.LONG_URL);
        String longUrl = iMatchStorageServiceImpl.getLongUrlCache(TestData.SHORT_URL);
        Assert.assertEquals(shortUrl, TestData.SHORT_URL);
        Assert.assertEquals(longUrl, TestData.LONG_URL);
    }
    @Test
    public void testGetUrlByCacheFail() {
        String shortUrl = iMatchStorageServiceImpl.getShortUrlCache(TestData.LONG_URL);
        String longUrl = iMatchStorageServiceImpl.getLongUrlCache(TestData.SHORT_URL);
    }
}
