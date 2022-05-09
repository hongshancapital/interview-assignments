package com.wangxiao.shortlink.applicaiton;

public interface ShortLinkService {
    String encodeUrl(String longLink);

    String decodeUrl(String shortLink);
}
