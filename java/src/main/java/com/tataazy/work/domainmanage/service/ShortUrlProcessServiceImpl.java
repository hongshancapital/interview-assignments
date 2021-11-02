package com.tataazy.work.domainmanage.service;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlProcessServiceImpl {
    public static final Log log = LogFactory.getLog(ShortUrlProcessServiceImpl.class);

    private volatile Map<String, String> recordMap;

    @PostConstruct
    public void init() {
        recordMap = new ConcurrentHashMap<>();
    }

    private void mapReInit() {
        if (null != recordMap && !recordMap.isEmpty()) {
            recordMap.clear();
        }
    }

    public String generateShorUrl(String normalUrl) {
        if (StringUtils.isBlank(normalUrl)) {
            return null;
        }
        String hashStr = Hashing.murmur3_32().hashString(normalUrl, StandardCharsets.UTF_8).toString();
        if (StringUtils.isNotBlank(recordMap.get(hashStr))) {
            return new StringBuilder(16).append(normalUrl,0,normalUrl.indexOf(":"))
                    .append("://").append(hashStr).toString();
        }
        try {
            recordMap.put(hashStr,normalUrl);
        } catch (Throwable e) {
            log.error("generateShorUrl exception : ", e);
            if (e instanceof OutOfMemoryError) {
                mapReInit();
            }
        }
        return new StringBuilder(16).append(normalUrl,0,normalUrl.indexOf(":"))
                .append("://").append(hashStr).toString();
    }

    public String getNormalUrl(String shortUrl) {
        if (StringUtils.isNotBlank(shortUrl)) {
            return recordMap.get(shortUrl.substring(shortUrl.lastIndexOf("/") + 1));
        }
        return null;
    }

}
