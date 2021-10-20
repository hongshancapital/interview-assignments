package com.assignment.service.impl;

import com.assignment.BaseTest;
import com.assignment.cache.UrlCache;
import com.assignment.common.utils.ShortUrlUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.*;

@PrepareForTest({UrlCache.class, ShortUrlUtils.class})
public class DomainServiceImplTest extends BaseTest {
    @InjectMocks
    private DomainServiceImpl domainService;
    public static final String longUrl = "www.baidu.com";
    public static final String shortUrl = "ADV";
    public static final String error_shortUrl = "adwfswkaj";

    @Test
    public void getShortUrlTest(){
        domainService.getShortUrl(null);

        domainService.getShortUrl(longUrl);
        UrlCache.clearCache();

        PowerMockito.mockStatic(UrlCache.class);
        PowerMockito.when(UrlCache.get(anyString())).thenReturn(null);
        domainService.getShortUrl(longUrl);
        UrlCache.clearCache();

        PowerMockito.when(UrlCache.get(anyString())).thenReturn(shortUrl);
        domainService.getShortUrl(longUrl);
        UrlCache.clearCache();

        PowerMockito.when(UrlCache.get(anyString())).thenReturn(null);

        PowerMockito.mockStatic(ShortUrlUtils.class);
        PowerMockito.when(ShortUrlUtils.getShortUrl()).thenReturn(null);
        domainService.getShortUrl(longUrl);
        UrlCache.clearCache();

        PowerMockito.mockStatic(ShortUrlUtils.class);
        PowerMockito.when(ShortUrlUtils.getShortUrl()).thenReturn(shortUrl);
        domainService.getShortUrl(longUrl);
        UrlCache.clearCache();

        PowerMockito.mockStatic(ShortUrlUtils.class);
        PowerMockito.when(ShortUrlUtils.getShortUrl()).thenReturn(error_shortUrl);
        domainService.getShortUrl(longUrl);
        UrlCache.clearCache();
    }

    @Test
    public void getLongUrlTest(){
        domainService.getLongUrl(null);

        domainService.getLongUrl(shortUrl);

        PowerMockito.mockStatic(UrlCache.class);
        PowerMockito.when(UrlCache.get(anyString())).thenReturn(null);
        domainService.getLongUrl(shortUrl);

        PowerMockito.when(UrlCache.get(anyString())).thenReturn(longUrl);
        domainService.getLongUrl(shortUrl);
    }
}
