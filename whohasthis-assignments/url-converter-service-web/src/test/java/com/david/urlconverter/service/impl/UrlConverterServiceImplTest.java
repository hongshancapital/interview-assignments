package com.david.urlconverter.service.impl;

import com.david.urlconverter.common.ResultStatusCode;
import com.david.urlconverter.common.UrlConverterException;
import com.david.urlconverter.service.web.IUrlConverterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
public class UrlConverterServiceImplTest {

    @Autowired
    private IUrlConverterService urlConverterService;

    private String longUrl = "https://www.baidu.com/23389dlw?320d-3=093id";

    private String invalidLongUrl = "http://173.3233.330.93:8390/938293";

    private String shortUrl;

    private String invalidShortUrl="c1F=d===";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGenerateShortUrlException() {
        //exception test
        assertThatThrownBy(() -> urlConverterService.generateShortUrl(invalidLongUrl))
                .isInstanceOf(UrlConverterException.class)
                .hasMessage(ResultStatusCode.ERROR_FORMAT_PARAM.msg());
    }

    @Test
    public void testGenerateShortUrl() {
        shortUrl = urlConverterService.generateShortUrl(longUrl);
        assertThat(urlConverterService.generateShortUrl(longUrl)).isEqualTo(shortUrl);
        String randomString = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
        log.info(urlConverterService.generateShortUrl(longUrl+randomString));
    }

    @Test
    public void testRetrieveLongUrlException() {
        //exception test
        assertThatThrownBy(() -> urlConverterService.retrieveLongUrl(invalidShortUrl))
                .isInstanceOf(UrlConverterException.class)
                .hasMessage(ResultStatusCode.ERROR_FORMAT_PARAM.msg());
    }

    @Test
    public void testRetrieveLongUrl() {
        shortUrl = urlConverterService.generateShortUrl(longUrl);
        assertThat(urlConverterService.retrieveLongUrl(shortUrl)).isEqualTo(longUrl);
    }

}