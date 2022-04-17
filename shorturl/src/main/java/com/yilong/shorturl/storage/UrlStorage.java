package com.yilong.shorturl.storage;

import com.yilong.shorturl.util.UrlShorter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class UrlStorage {

    private final String conflictPadding = "Vampire";
    private final long maximumSize;
    private final LongAdder counter;
    private final ConcurrentHashMap<String, String> storage;

    public UrlStorage(long maximumSize) {
        this.storage = new ConcurrentHashMap<>(10000);
        this.maximumSize = maximumSize;
        this.counter = new LongAdder();
    }

    private String getShortCode(String url) {
        String shortCode = UrlShorter.encodeUrl(url);
        String existValue;
        while ((existValue = storage.get(shortCode)) != null && !existValue.equals(url)) {
            url += this.conflictPadding;
            shortCode = UrlShorter.encodeUrl(url);
        }
        return existValue == null ? null: shortCode;
    }

    public String save(String url) {
        if (counter.longValue() >= this.maximumSize) {
            return this.getShortCode(url);
        }

        String paddingUrl = url;
        String shortCode = UrlShorter.encodeUrl(paddingUrl);
        String oldValue;
        while ((oldValue = storage.putIfAbsent(shortCode, url)) != null && !oldValue.equals(url)) {
//            if (log.isDebugEnabled()) {
//                log.debug(String.format("short code conflict, exist entity(%s,%s), conflict entity(%s,%s)",shortCode, oldValue, shortCode, url) );
//            }
            paddingUrl += this.conflictPadding;
            shortCode = UrlShorter.encodeUrl(paddingUrl);
        }
        if (oldValue == null) {
            counter.increment();
        }

        return shortCode;
    }

    public String get(String shortCode) {
        return storage.get(shortCode);
    }
}
