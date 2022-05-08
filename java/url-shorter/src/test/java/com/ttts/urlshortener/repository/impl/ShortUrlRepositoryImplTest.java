package com.ttts.urlshortener.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.domain.ShortUrl;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
class ShortUrlRepositoryImplTest {
    @ParameterizedTest
    @CsvSource({
        "1000, 90000",
        "1001, 90020",
        "1002, 90030"
    })
    public void add(Long id, Long surl) {
        ShortUrl value = new ShortUrl();
        value.setId(id);
        value.setSurl(surl);

        ShortUrlRepositoryImpl target = new ShortUrlRepositoryImpl();

        target.add(value);

        log.debug("URL映射数据:{}", ShortUrlRepositoryImpl.SHOT_URL_MAP);
        log.debug("surl-id索引:{}", ShortUrlRepositoryImpl.SURL_ID_MAP_INDEX);
    }

    @Test
    public void add_lack_capacity() {
        ShortUrlRepositoryImpl target = spy(new ShortUrlRepositoryImpl());

        doThrow(BusinessException.class).when(target).checkCapacity();

        assertThrows(BusinessException.class, () -> target.add(any()));
    }

    @Test
    public void checkCapacity() {
        ShortUrl m1 = mock(ShortUrl.class);
        ShortUrl m2 = mock(ShortUrl.class);
        ShortUrl m3 = mock(ShortUrl.class);

        Map<Long, ShortUrl> theMap = new ConcurrentHashMap<>();
        theMap.put(1L, m1);
        theMap.put(2L, m2);
        theMap.put(3L, m3);

        int theCapacity = 2;

        ShortUrlRepositoryImpl.SHOT_URL_MAP = theMap;
        ShortUrlRepositoryImpl.MAX_CAPACITY = theCapacity;

        ShortUrlRepositoryImpl target = new ShortUrlRepositoryImpl();

        assertThrows(BusinessException.class, () -> target.checkCapacity());
    }

    @ParameterizedTest
    @CsvSource({
        "3",
        "4",
        "8899999"
    })
    public void checkCapacity2(int theCapacity) {
        ShortUrl m1 = mock(ShortUrl.class);
        ShortUrl m2 = mock(ShortUrl.class);
        ShortUrl m3 = mock(ShortUrl.class);

        Map<Long, ShortUrl> theMap = new ConcurrentHashMap<>();
        theMap.put(1L, m1);
        theMap.put(2L, m2);
        theMap.put(3L, m3);

        ShortUrlRepositoryImpl.SHOT_URL_MAP = theMap;
        ShortUrlRepositoryImpl.MAX_CAPACITY = theCapacity;

        ShortUrlRepositoryImpl target = new ShortUrlRepositoryImpl();

        assertDoesNotThrow(() -> target.checkCapacity());
    }

    @Test
    public void getByLurl() {
        String lurl = "2344";
        ShortUrlRepositoryImpl target = new ShortUrlRepositoryImpl();

        List<ShortUrl> acutal =  target.getByLurl(lurl);
        log.debug("查询结果数量: {}", acutal.size());
    }

    @Test
    public void getBySurl() {
        Long surl = 346L;
        ShortUrlRepositoryImpl target = new ShortUrlRepositoryImpl();

        ShortUrl acutal =  target.getBySurl(surl);
        log.debug("查询结果: {}", acutal);
    }

    @Test
    public void deleteById() {
        Long id = 346L;
        ShortUrlRepositoryImpl target = new ShortUrlRepositoryImpl();

        target.deleteById(id);
    }
}