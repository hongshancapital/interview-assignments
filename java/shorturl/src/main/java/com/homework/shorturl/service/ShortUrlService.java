package com.homework.shorturl.service;

import com.homework.shorturl.api.ShorturlApiDelegate;
import com.homework.shorturl.model.LongShortMapModel;
import com.homework.shorturl.translator.Long2ShortUrlTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService implements ShorturlApiDelegate {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private Long2ShortUrlTranslator translator;

    @Override
    public ResponseEntity<LongShortMapModel> create(LongShortMapModel longurl) {
        String shortUrl = translator.getShortUrl(longurl.getLongUrl());
        LongShortMapModel mapModel = new LongShortMapModel().shortUrl(shortUrl);
        return new ResponseEntity<>(mapModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LongShortMapModel> queryLongUrl(String shortUrl) {
        return null;
    }
}
