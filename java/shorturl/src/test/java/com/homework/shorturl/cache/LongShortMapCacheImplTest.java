package com.homework.shorturl.cache;

import com.homework.shorturl.TestUtil;
import com.homework.shorturl.model.LongShortMapModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class LongShortMapCacheImplTest {
    @Autowired
    private LongShortMapCache cache;

    @Test
    void query_expect_return_the_same_with_add_after_add() {
        LongShortMapModel mapModel = new LongShortMapModel();
        mapModel.longUrl(TestUtil.randomLongUrl()).shortUrl(String.valueOf(20_000));
        cache.addOrUpdate(mapModel);

        Optional<LongShortMapModel> byLong = cache.getByLong(mapModel.getLongUrl());
        Assertions.assertEquals(true, byLong.isPresent());
        Assertions.assertEquals(mapModel.getShortUrl(), byLong.get().getShortUrl());
    }

    @Test
    void query_expect_empty_optional_without_add() {
        LongShortMapModel mapModel = new LongShortMapModel();
        mapModel.longUrl(TestUtil.randomLongUrl()).shortUrl(String.valueOf(20_001));

        Optional<LongShortMapModel> byLong = cache.getByLong(mapModel.getLongUrl());
        Assertions.assertEquals(true, byLong.isEmpty());
    }

    @Test
    void query_expect_return_the_same_with_readd() {
        final String shortUrl = String.valueOf(20_000);
        LongShortMapModel mapModel = new LongShortMapModel();
        mapModel.longUrl(TestUtil.randomLongUrl()).shortUrl(shortUrl);
        cache.addOrUpdate(mapModel);

        Optional<LongShortMapModel> byLong = cache.getByLong(mapModel.getLongUrl());
        Assertions.assertEquals(true, byLong.isPresent());
        Assertions.assertEquals(shortUrl, byLong.get().getShortUrl());

        // Re-Add
        cache.addOrUpdate(mapModel);
        byLong = cache.getByLong(mapModel.getLongUrl());
        Assertions.assertEquals(true, byLong.isPresent());
        Assertions.assertEquals(shortUrl, byLong.get().getShortUrl());
    }
}