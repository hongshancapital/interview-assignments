package com.homework.shorturl.service;

import com.homework.shorturl.api.ShorturlApiDelegate;
import com.homework.shorturl.model.RespEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService implements ShorturlApiDelegate {
    @Override
    public ResponseEntity<RespEntity> create(RespEntity longurl) {
        return null;
    }

    @Override
    public ResponseEntity<RespEntity> queryLongUrl(String shortUrl) {
        return null;
    }
}
