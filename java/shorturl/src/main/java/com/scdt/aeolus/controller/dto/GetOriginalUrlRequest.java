package com.scdt.aeolus.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetOriginalUrlRequest {
    @JsonProperty("ShortUrl")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }
}

