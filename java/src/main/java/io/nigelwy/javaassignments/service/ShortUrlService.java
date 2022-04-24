package io.nigelwy.javaassignments.service;

import io.nigelwy.javaassignments.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final UrlRepository repository;

    private final UrlGenerator generator;

    public String generateShortUrl(String originUrl) {
        var shortUrl = generator.generateShortUrl(originUrl);
        repository.save(shortUrl, originUrl);
        return shortUrl;
    }

    public String getOriginUrl(String shortUrl) {
        return repository.get(shortUrl);
    }
}
