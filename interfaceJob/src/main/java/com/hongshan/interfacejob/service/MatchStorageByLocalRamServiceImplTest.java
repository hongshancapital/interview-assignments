package com.hongshan.interfacejob.service;

import java.util.Optional;

import com.hongshan.interfacejob.service.impl.MatchStorageByLocalRamServiceImpl;
import com.hongshan.interfacejob.test.AbstractTest;
import com.hongshan.interfacejob.test.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MatchStorageByLocalRamServiceImplTest extends AbstractTest {

    @InjectMocks
    private MatchStorageByLocalRamServiceImpl matchStorageByLocalRamService;

    @Test
    public void testGetCacheSize() {
        matchStorageByLocalRamService.setUrlMatch(TestData.SHORT_URL, TestData.LONG_URL);
        long size = matchStorageByLocalRamService.getCacheSize();
        Assert.assertEquals(1L, size);
    }

    @Test
    public void testGetShortUrlByLongUrl() {
        matchStorageByLocalRamService.setUrlMatch(TestData.LONG_URL, TestData.SHORT_URL);
        Optional<String> longUrl = matchStorageByLocalRamService.getLongUrlByShortUrl(TestData.SHORT_URL);
        Assert.assertEquals(longUrl.get(), TestData.LONG_URL);
    }

    @Test
    public void testGetLongUrlByShortUrl() {
        matchStorageByLocalRamService.setUrlMatch(TestData.LONG_URL, TestData.SHORT_URL);
        Optional<String> shortUrl = matchStorageByLocalRamService.getShortUrlByLongUrl(TestData.LONG_URL);
        Assert.assertEquals(shortUrl.get(), TestData.SHORT_URL);
    }
}