package com.david.urlconverter.service.impl;

import com.david.urlconverter.service.dubbo.IUrlConverterStorageSOAService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class UrlConverterStorageSOAServiceImplTest {

    @Autowired
    private IUrlConverterStorageSOAService urlConverterStorageSOAService;

    private String longUrl = "longUrlStorageTest";

    private String shortUrl = "storageTestShortUrl";

    @Test
    public void retrieveLongUrl() {
        urlConverterStorageSOAService.storeUrlMapping(shortUrl, longUrl);
        assertThat(urlConverterStorageSOAService.retrieveLongUrl(shortUrl)).isEqualTo(longUrl);
        assertThat(urlConverterStorageSOAService.retrieveLongUrl(shortUrl+"abc")).isEqualTo(null);
    }

    @Test
    public void queryShortUrl() {
        urlConverterStorageSOAService.storeUrlMapping(shortUrl, longUrl);
        assertThat(urlConverterStorageSOAService.queryShortUrl(longUrl)).isEqualTo(shortUrl);
        assertThat(urlConverterStorageSOAService.retrieveLongUrl(shortUrl+"abc")).isEqualTo(null);
    }

    @Test
    public void storeUrlMapping() {
        urlConverterStorageSOAService.storeUrlMapping(shortUrl, longUrl);
        assertThat(urlConverterStorageSOAService.retrieveLongUrl(shortUrl)).isEqualTo(longUrl);
        assertThat(urlConverterStorageSOAService.queryShortUrl(longUrl)).isEqualTo(shortUrl);
        assertThat(urlConverterStorageSOAService.retrieveLongUrl(shortUrl+"abc")).isEqualTo(null);
        assertThat(urlConverterStorageSOAService.retrieveLongUrl(shortUrl+"abc")).isEqualTo(null);
    }
}
