package com.ttts.urlshortener.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.domain.LUrlReq;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.domain.ShortUrlVO;
import com.ttts.urlshortener.service.LongValueCreateService;
import com.ttts.urlshortener.service.ShortUrlService;
import com.ttts.urlshortener.service.UrlQueryService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

class ShortUrlCreateServiceImplTest {

    @Test
    void create_validParams_fail() {
        LongValueCreateService longValueCreateService = mock(LongValueCreateService.class);
        ShortUrlCreateServiceImpl target = spy(new ShortUrlCreateServiceImpl(longValueCreateService, null, null));

        doThrow(BusinessException.class)
            .when(target)
            .validParams(any(LUrlReq.class));

        verify(target, times(0)).internalCreate(anyString());
        assertThrows(BusinessException.class, () -> target.create(any(LUrlReq.class)));
    }

    @Test
    void create_validParams_ok() {
        String shorturlDomain = "http:we.com";
        String input = "1234";

        String surl62 = "456";
        long surl10 = 15692L;


        LUrlReq req = mock(LUrlReq.class);
        doReturn(input).when(req).getLurl();

        ShortUrl shortUrl = mock(ShortUrl.class);
        doReturn(surl10).when(shortUrl).getSurl();

        LongValueCreateService longValueCreateService = mock(LongValueCreateService.class);
        ShortUrlCreateServiceImpl target = spy(new ShortUrlCreateServiceImpl(longValueCreateService, null, null));

        doNothing().when(target).validParams(any(LUrlReq.class));
        doReturn(shortUrl).when(target).internalCreate(anyString());
        doReturn(shorturlDomain).when(target).getShortUrlDomain();

        ShortUrlVO actual = target.create(req);

        String except = shorturlDomain + "/" + surl62;

        verify(target, times(1)).validParams(req);
        verify(target, times(1)).internalCreate(input);
        assertEquals(except, actual.getNewUrl());
    }

    @Test
    void internalCreate_exist() {
        LongValueCreateService longValueCreateService = mock(LongValueCreateService.class);
        UrlQueryService urlQueryService = mock(UrlQueryService.class);

        String input = "123";
        String existSurl = "456";

        ShortUrl shortUrl = mock(ShortUrl.class);

        doReturn(Optional.of(shortUrl)).when(urlQueryService).queryShortUrl(anyString());

        ShortUrlService shortUrlService = mock(ShortUrlService.class);

        ShortUrlCreateServiceImpl target = spy(new ShortUrlCreateServiceImpl(longValueCreateService, urlQueryService, shortUrlService));
        doReturn(true).when(target).isDoneFuzzy(anyString());

        ShortUrl actual = target.internalCreate(input);

        assertEquals(shortUrl, actual);
        verify(longValueCreateService, times(0)).create(input);
    }

    @Test
    void internalCreate_exception() {
        LongValueCreateService longValueCreateService = null;
        UrlQueryService urlQueryService = mock(UrlQueryService.class);

        doThrow(BusinessException.class).when(urlQueryService).queryShortUrl(anyString());

        ShortUrlService shortUrlService = mock(ShortUrlService.class);

        ShortUrlCreateServiceImpl target = spy(new ShortUrlCreateServiceImpl(longValueCreateService, urlQueryService, shortUrlService));
        doReturn(true).when(target).isDoneFuzzy(anyString());

        assertThrows(BusinessException.class, () -> target.internalCreate(anyString()));
    }

    @Test
    void internalCreate_exception_2() {
        String input = "123";

        LongValueCreateService longValueCreateService = mock(LongValueCreateService.class);
        doThrow(BusinessException.class).when(longValueCreateService).create(input);

        UrlQueryService urlQueryService = mock(UrlQueryService.class);
        doReturn(Optional.empty()).when(urlQueryService).queryShortUrl(anyString());

        ShortUrlService shortUrlService = mock(ShortUrlService.class);

        ShortUrlCreateServiceImpl target = spy(new ShortUrlCreateServiceImpl(longValueCreateService, urlQueryService, shortUrlService));


        assertThrows(BusinessException.class, () -> target.internalCreate(input));
    }


    @ParameterizedTest
    @CsvSource({
        "0",
        "4000",
        "5000",
    })
    void internalCreate_ok(long lValue) {
        String input = "123";

        LongValueCreateService longValueCreateService = mock(LongValueCreateService.class);
        doReturn(lValue).when(longValueCreateService).create(anyString());

        UrlQueryService urlQueryService = mock(UrlQueryService.class);
        doReturn(Optional.empty()).when(urlQueryService).queryShortUrl(anyString());

        ShortUrl shortUrl = mock(ShortUrl.class);

        ShortUrlService shortUrlService = mock(ShortUrlService.class);
        doReturn(shortUrl).when(shortUrlService).add(lValue, input);

        ShortUrlCreateServiceImpl target = spy(new ShortUrlCreateServiceImpl(longValueCreateService, urlQueryService, shortUrlService));
        doReturn(false).when(target).isDoneFuzzy(anyString());

        ShortUrl actual = target.internalCreate(input);

        assertEquals(shortUrl, actual);
        verify(shortUrlService, times(1)).add(anyLong(), anyString());
        verify(urlQueryService, times(0)).queryLurl(anyString());

    }

    @ParameterizedTest
    @NullSource
    public void validParams(LUrlReq req) {
        ShortUrlCreateServiceImpl target =
            new ShortUrlCreateServiceImpl(null, null, null);

        assertThrows(BusinessException.class, () -> target.validParams(req));
    }

    @ParameterizedTest
    @CsvSource({
        "1234, false",
        "etregreh, false",
        "www.baidu.com, false",
        "127.0.0.1:8080, false",
        "127.0.0.1, false",
        "localhost, false",
        "localhost:8080, false",
    })
    public void validParams(String url) {
        LUrlReq req = mock(LUrlReq.class);
        doReturn(url).when(req).getLurl();

        ShortUrlCreateServiceImpl target =
            new ShortUrlCreateServiceImpl(null, null, null);

        assertThrows(BusinessException.class, () -> target.validParams(req));
    }

    @ParameterizedTest
    @CsvSource({
        "http://www.baidu.com",
        "https://www.baidu.com",
        "http:127.0.0.1:8080/dgreg",
        "http:localhost:8080/sdg",
    })
    public void validParams_success(String url) {
        LUrlReq req = mock(LUrlReq.class);
        doReturn(url).when(req).getLurl();

        ShortUrlCreateServiceImpl target =
            new ShortUrlCreateServiceImpl(null, null, null);

        assertDoesNotThrow(() -> target.validParams(req));
    }
}