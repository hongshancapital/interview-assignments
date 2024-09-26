package net.sky.demo.url.mapping.service;

import net.sky.demo.url.mapping.store.Long2ShortMappingStore;
import net.sky.demo.url.mapping.store.Short2LongMappingStore;
import net.sky.demo.url.mapping.util.Long2StringUtil;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class UrlMappingServiceTest {

    @Test
    public void generateShortUrl() {
        UrlMappingService urlMappingService = new UrlMappingService();
        String longUrl = "http://www.xxx.com";
        long number = 100L;

        Long2ShortMappingStore long2ShortMappingStore = EasyMock.mock(Long2ShortMappingStore.class);
        EasyMock.expect(long2ShortMappingStore.queryBySourceUrl(longUrl)).andReturn(null);


        Short2LongMappingStore short2LongMappingStore = EasyMock.mock(Short2LongMappingStore.class);
        EasyMock.expect(short2LongMappingStore.generateNumber(longUrl)).andReturn(number);
        long2ShortMappingStore.insertNewMapping(longUrl, number);
        EasyMock.expectLastCall();


        EasyMock.replay(long2ShortMappingStore);
        EasyMock.replay(short2LongMappingStore);
        urlMappingService.setShort2LongMappingStore(short2LongMappingStore);
        urlMappingService.setLong2ShortMappingStore(long2ShortMappingStore);


        Assert.assertTrue(urlMappingService.generateShortUrl(longUrl).equals(Long2StringUtil.long2String(number)));


        EasyMock.reset(long2ShortMappingStore);
        EasyMock.expect(long2ShortMappingStore.queryBySourceUrl(longUrl)).andReturn(number);
        EasyMock.replay(long2ShortMappingStore);
        urlMappingService.setShort2LongMappingStore(null);
        Assert.assertTrue(urlMappingService.generateShortUrl(longUrl).equals(Long2StringUtil.long2String(number)));
    }

    @Test
    public void querySourceUrl() {
        UrlMappingService urlMappingService = new UrlMappingService();

        String shortUrl = "5CBD";
        long shortNumber = Long2StringUtil.string2long(shortUrl);
        String longUrl = "http://www.xxx.com";

        Short2LongMappingStore short2LongMappingStore = EasyMock.mock(Short2LongMappingStore.class);
        EasyMock.expect(short2LongMappingStore.getSourceUrlByNumber(shortNumber)).andReturn(longUrl);
        EasyMock.replay(short2LongMappingStore);
        urlMappingService.setShort2LongMappingStore(short2LongMappingStore);

        Assert.assertTrue(urlMappingService.querySourceUrl(shortUrl).equals(longUrl));


    }
}