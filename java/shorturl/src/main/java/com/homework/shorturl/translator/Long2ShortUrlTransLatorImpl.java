package com.homework.shorturl.translator;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Long2ShortUrlTransLatorImpl implements Long2ShortUrlTranslator{
    private static final AtomicInteger serialNo = new AtomicInteger(0);

    @Override
    public String getShortUrl(String longUrl) {
        return String.valueOf(serialNo.getAndIncrement());
    }
}
