package com.polly.shorturl.tools;

import com.polly.shorturl.cache.UrlCache;
import com.polly.shorturl.entity.ShortUrl;

/**
 * @author polly
 * @date 2022.03.21 00:03:11
 */
public class ShortUrlHandler {

    private SnowflakeIdWorker worker;

    private UrlTransfer transfer;

    private UrlCache cache;

    public ShortUrlHandler(SnowflakeIdWorker worker, UrlTransfer transfer, UrlCache cache) {
        this.worker = worker;
        this.transfer = transfer;
        this.cache = cache;
    }

    public ShortUrl insert(String url) {
        String shortUrl = transfer.convert(url);
        if (cache.containsUrl(shortUrl)) {
            return cache.get(shortUrl);
        }
        long id = worker.nextId();
        ShortUrl obj = new ShortUrl(id, url);
        cache.put(shortUrl, obj);
        return obj;
    }

    public ShortUrl getByShortUrl(String url) {
        return cache.get(url);
    }
}
