package com.scdt.aeolus.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetShortUrlResponse extends BaseInnerResponse {
    @JsonProperty("ShortUrl")
    private String shortUrl;

    public GetShortUrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
