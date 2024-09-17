package com.scdt.aeolus.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetShortUrlRequest {
    @JsonProperty("OriginalUrl")
    private String OriginalUrl;

    public String getOriginalUrl() {
        return OriginalUrl;
    }
}

