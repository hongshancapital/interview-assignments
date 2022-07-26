package com.scdt.url.tiny_url.representation;

import com.scdt.url.common.ddd.Representation;

import java.time.Instant;

public class TinyUrlRepresentation implements Representation {
    private final String id;
    private final String originalUrl;
    private final Instant createdAt;

    public TinyUrlRepresentation(String id,
                                 String originalUrl,
                                 Instant createdAt) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
    }
}
