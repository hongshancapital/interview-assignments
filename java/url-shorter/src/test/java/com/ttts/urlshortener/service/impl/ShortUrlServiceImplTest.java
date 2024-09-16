package com.ttts.urlshortener.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import cn.hutool.crypto.SecureUtil;
import com.ttts.urlshortener.business.ShortUrlBusiness;
import com.ttts.urlshortener.domain.ShortUrl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ShortUrlServiceImplTest {
    @ParameterizedTest
    @CsvSource({
        "0000, 123",
        "4000, 234",
        "5000, \"34577\"",
    })
    public void add(Long surl, String lurl) {
        ShortUrlBusiness shortUrlBusiness = mock(ShortUrlBusiness.class);

        ShortUrlServiceImpl target = new ShortUrlServiceImpl(shortUrlBusiness);

        ShortUrl actual = target.add(surl, lurl);

        assertEquals(surl, actual.getSurl());
        assertEquals(lurl, actual.getLurl());
        assertEquals(SecureUtil.md5(lurl), actual.getLmd5());

        verify(shortUrlBusiness, times(1)).add(actual);
    }

    @Test
    public void deleteById() {
        ShortUrlBusiness shortUrlBusiness = mock(ShortUrlBusiness.class);

        ShortUrlServiceImpl target = new ShortUrlServiceImpl(shortUrlBusiness);

        target.deleteById(anyLong());

        verify(shortUrlBusiness, times(1)).deleteById(anyLong());
    }

    @Test
    public void getBySurl() {
        Long surl = 1234L;
        ShortUrl m = mock(ShortUrl.class);

        ShortUrlBusiness shortUrlBusiness = mock(ShortUrlBusiness.class);
        doReturn(m).when(shortUrlBusiness).getBySurl(surl);

        ShortUrlServiceImpl target = new ShortUrlServiceImpl(shortUrlBusiness);

        ShortUrl actual = target.getBySurl(surl);

        assertEquals(m, actual);
    }

    @Test
    public void getByLurl() {
        String lurl = "23445";
        List<ShortUrl> ms = mock(List.class);

        ShortUrlBusiness shortUrlBusiness = mock(ShortUrlBusiness.class);
        doReturn(ms).when(shortUrlBusiness).getByLurl(lurl);

        ShortUrlServiceImpl target = new ShortUrlServiceImpl(shortUrlBusiness);

        List<ShortUrl> actual = target.getByLurl(lurl);

        assertEquals(ms, actual);
    }
}
