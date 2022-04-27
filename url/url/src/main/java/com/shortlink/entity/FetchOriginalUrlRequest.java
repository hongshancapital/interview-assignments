package com.shortlink.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FetchOriginalUrlRequest{

    private String shortLink;

    private String reqId;

    private Integer appId;
}
