package com.shortlink.util;

import com.shortlink.entity.CreateShortLinkRequest;
import com.shortlink.entity.FetchOriginalUrlRequest;

public class ConvertRequestUtil {

    public static CreateShortLinkRequest createShortLinkRequest(String reqId, String url, Integer appId) {

        return CreateShortLinkRequest.builder().url(url).reqId(reqId).appId(appId).build();
    }

    public static FetchOriginalUrlRequest fetchOriginalUrlRequest(String reqId, String url, Integer appId) {

        return FetchOriginalUrlRequest.builder().shortLink(url).appId(appId).reqId(reqId).build();
    }
}
