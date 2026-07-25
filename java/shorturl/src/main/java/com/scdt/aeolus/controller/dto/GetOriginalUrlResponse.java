package com.scdt.aeolus.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GetOriginalUrlResponse extends BaseInnerResponse {
    @JsonProperty("OriginalUrl")
    private String originalUrl;

    public GetOriginalUrlResponse(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
