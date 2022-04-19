package com.mortimer.shortenurl.component.cache;

import com.mortimer.shortenurl.component.sequence.SequenceGenerator;
import com.mortimer.shortenurl.util.Base62;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CacheManager {
    @Autowired
    private UrlMappingCache shortUrlLongUrlMapping;

    @Autowired
    private UrlMappingCache shortUrlTooLongUrlMapping;

    @Autowired
    private UrlMappingCache longUrlShortUrlMapping;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    /**
     * 由longUrl获取对应的shortUrl
     *
     * @param longUrl   长URL
     * @return  longUrl对应的shortUrl，可能为null
     */
    public String getShortUrl(String longUrl) {
        if (longUrl == null) {
            log.warn("longUrl 不能为null");
            return null;
        }
        // 统一格式，避免longUrl中出现过多字符
        longUrl = UriUtils.decode(longUrl, StandardCharsets.UTF_8);
        String shortUrl = longUrlShortUrlMapping.get(longUrl);
        if (shortUrl == null) {
            shortUrl = Base62.getInstance().encode(sequenceGenerator.nextId());
            if (longUrl.length() <= 500) {
                shortUrlLongUrlMapping.put(shortUrl, longUrl);
            } else {
                shortUrlTooLongUrlMapping.put(shortUrl, longUrl);
            }
            longUrlShortUrlMapping.put(longUrl, shortUrl);
        }
        return shortUrl;
    }

    /**
     * 由shortUrl获取对应的longUrl
     *
     * @param shortUrl  短URL
     * @return  shortUrl对应的longUrl，可能为null
     */
    public String getLongUrl(String shortUrl) {
        String longUrl = shortUrlLongUrlMapping.get(shortUrl);
        if (longUrl != null) {
            return longUrl;
        }
        return shortUrlTooLongUrlMapping.get(shortUrl);
    }
}
