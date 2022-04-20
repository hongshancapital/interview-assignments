package pers.zhufan.shorturl.service;

import pers.zhufan.shorturl.config.ShortUrlConfig;
import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;

public interface ShortUrlService {

    public ShorterUrl generalShortUrl(ShortUrlConfig config, String longUrl);

    public String recoverLongUrl(String shortUrl);

}
