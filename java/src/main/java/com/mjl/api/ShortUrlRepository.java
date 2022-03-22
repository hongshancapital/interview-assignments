package com.mjl.api;

public interface ShortUrlRepository {

    String getLongUrlFromShortUrlSuffix(String shortUrl);

    String getShortUrlSuffixFromLongUrl(String longUrl);

    void rmOverdueUrl();

    void saveUrlMap(String longUrl, String shortUrl);

}
