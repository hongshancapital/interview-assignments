package com.shortUrlTest;

import com.service.impl.MatchStorageServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

/**
 * @Author jeffrey
 * @Date 2021/10/12
 * @description:
 */
public class MatchStorageByLocalRamServiceImplTest extends AbstractTest {

    @InjectMocks
    private MatchStorageServiceImpl matchStorageService;

    @Test
    public void testGetCacheSize() {
        matchStorageService.setUrlMatch(TestData.SHORT_URL, TestData.LONG_URL);
        long size = matchStorageService.getCacheSize();
        Assert.assertEquals(1L, size);
    }

    @Test
    public void testGetShortUrlByLongUrl() {
        matchStorageService.setUrlMatch(TestData.LONG_URL, TestData.SHORT_URL);
        Optional<String> longUrl = matchStorageService.getLongUrlByShortUrl(TestData.SHORT_URL);
        Assert.assertEquals(longUrl.get(), TestData.LONG_URL);
    }

    @Test
    public void testGetLongUrlByShortUrl() {
        matchStorageService.setUrlMatch(TestData.LONG_URL, TestData.SHORT_URL);
        Optional<String> shortUrl = matchStorageService.getShortUrlByLongUrl(TestData.LONG_URL);
        Assert.assertEquals(shortUrl.get(), TestData.SHORT_URL);
    }
}
