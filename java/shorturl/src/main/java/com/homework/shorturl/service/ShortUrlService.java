package com.homework.shorturl.service;

import com.homework.shorturl.api.ShorturlApiDelegate;
import com.homework.shorturl.model.LongShortMapModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ShortUrlService implements ShorturlApiDelegate {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static AtomicInteger serialNo = new AtomicInteger(0);

    @Override
    public ResponseEntity<LongShortMapModel> create(LongShortMapModel longurl) {
        int num = serialNo.getAndIncrement();
        LongShortMapModel mapModel = new LongShortMapModel().shortUrl(String.valueOf(num));
        return new ResponseEntity<>(mapModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LongShortMapModel> queryLongUrl(String shortUrl) {
        return null;
    }
}
