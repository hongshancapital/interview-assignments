package com.homework.shorturl.service;

import com.homework.shorturl.api.ShorturlApiDelegate;
import com.homework.shorturl.cache.LongShortMapCache;
import com.homework.shorturl.model.LongShortMapModel;
import com.homework.shorturl.translator.Long2ShortUrlTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortUrlService implements ShorturlApiDelegate {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private Long2ShortUrlTranslator translator;

    @Autowired
    private LongShortMapCache cache;

    @Override
    public ResponseEntity<LongShortMapModel> create(LongShortMapModel longurl) {
        LOGGER.info("create begin");
        Optional<LongShortMapModel> byLong = cache.getByLong(longurl.getLongUrl());
        if (byLong.isPresent()) {
            LOGGER.info("create already in cache");
            return new ResponseEntity<>(byLong.get(), HttpStatus.CREATED);
        }
        if (alreadyFullAndDenyCreateNew()) {
            LOGGER.warn("create short url failed for INSUFFICIENT_STORAGE");
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).build();
        }
        String shortUrl = translator.getShortUrl(longurl.getLongUrl());
        LongShortMapModel mapModel = new LongShortMapModel().longUrl(longurl.getLongUrl()).shortUrl(shortUrl);
        cache.addOrUpdate(mapModel);
        return new ResponseEntity<>(mapModel, HttpStatus.CREATED);
    }

    private boolean alreadyFullAndDenyCreateNew() {
        return cache.isFull();
    }

    @Override
    public ResponseEntity<LongShortMapModel> queryLongUrl(String shortUrl) {
        LOGGER.info("query long url");
        return cache.getByShort(shortUrl)
                .map(mapModel -> new ResponseEntity<>(mapModel, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }
}
