package com.ttts.urlshortener.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.service.ShortUrlService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class UrlQueryServiceImplTest {

    @Test
    void queryLurl() {
        String surl = "345";

        String lurl = "4566";

        ShortUrl m = mock(ShortUrl.class);
        doReturn(lurl).when(m).getLurl();

        ShortUrlService shortUrlService = mock(ShortUrlService.class);
        doReturn(m).when(shortUrlService).getBySurl(anyLong());

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(shortUrlService));

        doReturn(Optional.of(m)).when(target).checkExpired(m);

        String actual = target.queryLurl(surl);

        assertEquals(lurl, actual);
    }



    @Test
    void queryLurl_null() {
        String surl = "345";

        ShortUrlService shortUrlService = mock(ShortUrlService.class);
        doReturn(null).when(shortUrlService).getBySurl(anyLong());

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(shortUrlService));

        String actual = target.queryLurl(surl);

        assertEquals(null, actual);
    }

    @Test
    void queryLurl_Expired() {
        String surl = "345";

        String lurl = "4566";

        ShortUrl m = mock(ShortUrl.class);
        doReturn(lurl).when(m).getLurl();

        ShortUrlService shortUrlService = mock(ShortUrlService.class);
        doReturn(m).when(shortUrlService).getBySurl(anyLong());

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(shortUrlService));

        doReturn(Optional.empty()).when(target).checkExpired(m);

        String actual = target.queryLurl(surl);

        assertEquals(null, actual);
    }

    @Test
    void querySurl() {

    }

    //过期
    @Test
    void checkExpired() {
        Long id = 123L;
        ShortUrl shortUrl = mock(ShortUrl.class);
        doReturn(LocalDateTime.now().plusHours(1)).when(shortUrl).getExpiresTime();
        doReturn(id).when(shortUrl).getId();

        ShortUrlService shortUrlService = mock(ShortUrlService.class);

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(shortUrlService));

        Optional<ShortUrl> actual = target.checkExpired(shortUrl);

        assertTrue(actual.isPresent());
        verify(shortUrlService, times(0)).deleteById(id);
    }

    //过期
    @Test
    void checkExpired_expired() {
        Long id = 123L;
        ShortUrl shortUrl = mock(ShortUrl.class);
        doReturn(LocalDateTime.now().plusHours(-1)).when(shortUrl).getExpiresTime();
        doReturn(id).when(shortUrl).getId();

        ShortUrlService shortUrlService = mock(ShortUrlService.class);

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(shortUrlService));

        Optional<ShortUrl> actual = target.checkExpired(shortUrl);

        assertFalse(actual.isPresent());
        verify(shortUrlService, times(1)).deleteById(id);
    }

    @Test
    void checkExpired_expiredTime_null() {

        ShortUrl shortUrl = mock(ShortUrl.class);
        doReturn(null).when(shortUrl).getExpiresTime();

        ShortUrlService shortUrlService = mock(ShortUrlService.class);

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(shortUrlService));

        Optional<ShortUrl> actual = target.checkExpired(shortUrl);

        assertFalse(actual.isPresent());
        verify(shortUrlService, times(0)).deleteById(anyLong());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testCheckExpired(List<ShortUrl> records) {

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(null));

        List<ShortUrl> actual = target.checkExpired(records);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testCheckExpired2() {
        ShortUrl m1 = mock(ShortUrl.class);
        ShortUrl m2 = mock(ShortUrl.class);
        ShortUrl m3 = mock(ShortUrl.class);

        List<ShortUrl> records = new ArrayList<>();
        records.add(m1);
        records.add(m2);
        records.add(m3);

        UrlQueryServiceImpl target = spy(new UrlQueryServiceImpl(null));

        doReturn(Optional.of(m1)).when(target).checkExpired(m1);
        doReturn(Optional.empty()).when(target).checkExpired(m2);
        doReturn(Optional.of(m3)).when(target).checkExpired(m3);

        List<ShortUrl> actual = target.checkExpired(records);
        assertEquals(2, actual.size());
    }
}