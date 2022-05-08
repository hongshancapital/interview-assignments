package com.ttts.urlshortener.business.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.repository.ShortUrlRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ShortUrlBusinessImplTest {

    @Test
    void add() {
        int result = 1;
        ShortUrl value = mock(ShortUrl.class);
        ShortUrlRepository repository = mock(ShortUrlRepository.class);
        doReturn(result).when(repository).add(value);

        ShortUrlBusinessImpl target = new ShortUrlBusinessImpl(repository);

        int actual = target.add(value);

        assertEquals(result, actual);
        verify(repository, times(1)).add(value);
    }

    @Test
    void deleteById() {
        Long id = 1234L;

        ShortUrlRepository repository = mock(ShortUrlRepository.class);

        ShortUrlBusinessImpl target = new ShortUrlBusinessImpl(repository);

        target.deleteById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void getBySurl() {
        Long surl = 1234L;
        ShortUrl result = mock(ShortUrl.class);

        ShortUrlRepository repository = mock(ShortUrlRepository.class);
        doReturn(result).when(repository).getBySurl(surl);

        ShortUrlBusinessImpl target = new ShortUrlBusinessImpl(repository);

        ShortUrl actual = target.getBySurl(surl);

        assertEquals(result, actual);
    }

    @Test
    void getByLurl() {
        String lurl = "3456";
        List<ShortUrl> result = mock(List.class);

        ShortUrlRepository repository = mock(ShortUrlRepository.class);
        doReturn(result).when(repository).getByLurl(lurl);

        ShortUrlBusinessImpl target = new ShortUrlBusinessImpl(repository);

        List<ShortUrl> actual = target.getByLurl(lurl);

        assertIterableEquals(result, actual);
    }
}