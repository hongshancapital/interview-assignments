package com.scdt.china.shorturl.service.internal;

import com.scdt.china.shorturl.service.ShortUrlGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RandomShortUrlGenerator implements ShortUrlGenerator {

    @Override
    public String generateShortUrl(String url) {
        return RandomStringUtils.random(Math.abs(url.hashCode() % 7) + 1,true,true);
    }

    @Override
    public String generateShortUrlWhenConflicted(String url, String conflicetdShortUrl) {
        return RandomStringUtils.random(8,true,true);
    }
}
