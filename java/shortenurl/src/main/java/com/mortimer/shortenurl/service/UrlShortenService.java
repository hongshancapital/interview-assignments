package com.mortimer.shortenurl.service;

import com.mortimer.shortenurl.component.cache.CacheManager;
import com.mortimer.shortenurl.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenService {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ConfigProperties configProperties;

    @Value("${server.port:8080}")
    private int port;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    public String createShortUrl(String longUrl) {
        String shortUrl = cacheManager.getShortUrl(longUrl);
        return getDomain(shortUrl);
    }

    /**
     * 根据短链生成长URL
     *
     * @param shortUrl  短链，不含域名、端口等信息
     * @return  短链对应的长URL
     */
    public String getLongUrl(String shortUrl) {
        return cacheManager.getLongUrl(shortUrl);
    }

    private String getDomain(String shortUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(configProperties.getDomain())
                .append(':').append(port)
                .append(contextPath)
                .append(shortUrl);
        return sb.toString();
    }
}
