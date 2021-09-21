package com.service;

import static org.mockito.ArgumentMatchers.anyString;

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

public class UrlTransformServiceImplTest extends AbstractTest {

    @InjectMocks
    private UrlTransformServiceImpl urlTransformServiceImpl;
    @Mock
    private MatchStorageServiceImpl iMatchStorageServiceImpl;
    @Test
    public void testGetShortUrlSuccess() {
        String shortUrl = urlTransformServiceImpl.getShortUrl(anyString());
    }
    @Test
    public void testGetShortUrlByCacheSuccess() {
        Mockito.when(iMatchStorageServiceImpl.getShortUrlCache(anyString())).thenReturn(TestData.SHORT_URL);
        String shortUrl = urlTransformServiceImpl.getShortUrl(anyString());
        Assert.assertEquals(shortUrl, TestData.SHORT_URL);
    }
    @Test
    public void testGetShortUrlFail() {
        Mockito.when(iMatchStorageServiceImpl.getShortUrlCacheSize()).thenReturn(10000);
        try{
            String shortUrl1 = urlTransformServiceImpl.getShortUrl(TestData.LONG_URL);
        }catch (SystemException ex){
            Assert.assertEquals(String.valueOf(ex.getCode()), String.valueOf(CommonConstants.CODE_SERVICE_UNAVAILABLE));
        }
    }
    @Test
    public void testGetLongUrl() {
        try{
            String longUrl = urlTransformServiceImpl.getLongUrl(anyString());
        }catch (SystemException ex){
            Assert.assertEquals(String.valueOf(ex.getCode()), String.valueOf(CommonConstants.CODE_LONG_URL_NOT_FOUND));
        }
    }
    @Test
    public void testGetLongUrlSucc() {
        Mockito.when(iMatchStorageServiceImpl.getLongUrlCache(anyString())).thenReturn(TestData.LONG_URL);
        String longUrl = urlTransformServiceImpl.getLongUrl(anyString());
        Assert.assertEquals(longUrl, TestData.LONG_URL);
    }
    @Test
    public void testGetLongUrlFail() {
        try{
            String longUrl = urlTransformServiceImpl.getLongUrl(anyString());
        }catch (SystemException ex){
            Assert.assertEquals(String.valueOf(ex.getCode()), String.valueOf(CommonConstants.CODE_LONG_URL_NOT_FOUND));
        }

    }
}
