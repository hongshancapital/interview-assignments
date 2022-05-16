package com.redwood.urlshorter.services;

import com.redwood.urlshorter.model.SavedShortUrl;
import com.redwood.urlshorter.repositories.ShortUrl;
import com.redwood.urlshorter.repositories.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.redwood.urlshorter.model.NewUrl;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UrlShortenerService {

    private static final byte[] VALID_CHARS = "ABCEDFGHIJKLMNOPQRSTUVWXYZabcedefghijklmnopqrstuvwxyz0123456789-_".getBytes();
    private static final int SHORT_URL_LEN = 8;
    private final ShortUrlRepository repository;

    public SavedShortUrl put(NewUrl url) {
        if (!new UrlValidator().isValid(url.getUrl())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Url.");
        }
        final ShortUrl found = repository.findByUrl(url.getUrl());
        if (found != null) {
            return new SavedShortUrl().setUrl(found.getUrl()).setKey(found.getKey());
        }
        final ShortUrl shortUrl = new ShortUrl().setUrl(url.getUrl());
        final ShortUrl saved = repository.create(shortUrl.setKey(createUniqueKey()));
        return new SavedShortUrl().setUrl(saved.getUrl()).setKey(saved.getKey());
    }

    private String createUniqueKey() {
        String key;
        do {
            final byte[] newKey = new byte[SHORT_URL_LEN];
            final Random rnd = new Random();
            for (int i = 0; i < SHORT_URL_LEN; i++) {
                newKey[i] = VALID_CHARS[rnd.nextInt(VALID_CHARS.length)];
            }
            key = new String(newKey, StandardCharsets.UTF_8);
        } while (repository.findByKey(key) != null);
        return key;
    }

    public SavedShortUrl getByKey(String key) {
        ShortUrl saved = repository.findByKey(key);
        return new SavedShortUrl().setUrl(saved.getUrl()).setKey(saved.getKey());
    }
}

