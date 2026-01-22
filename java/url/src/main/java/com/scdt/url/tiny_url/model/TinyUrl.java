package com.scdt.url.tiny_url.model;

import com.scdt.url.common.ddd.AggregateRoot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TinyUrl implements AggregateRoot {

    private TinyUrlId id;
    private String originalUrl;
    private Instant createdAt;

    public static TinyUrl create(TinyUrlId tinyUrlId, String originalUrl) {
        return new TinyUrl(tinyUrlId, originalUrl, Instant.now());
    }
}
