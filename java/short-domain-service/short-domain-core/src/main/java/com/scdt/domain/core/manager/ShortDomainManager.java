package com.scdt.domain.core.manager;

import com.google.common.collect.Maps;
import com.scdt.domain.api.common.Response;
import com.scdt.domain.core.common.util.ShortUrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;

/**
 * @author tuxiaozhou
 * @date 2022/4/3
 */
@Component
public class ShortDomainManager {

    private static final String SHORT_URL_FORMAT = "http://%s/api/long-url/%s";
    private final Map<String, String> shortUrlMap = Maps.newConcurrentMap();
    private final Map<String, String> longUrlMap = Maps.newConcurrentMap();

    @Value("${short-domain.host:}")
    private String shortDomainHost;
    @Value("${short-domain.maximum:10000}")
    private Integer shortDomainMaximum;

    public Response<String> createShortUrl(String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            return Response.error("长域名不可为空");
        }
        if (!isValidUrl(longUrl)) {
            return Response.error("长域名格式有误");
        }
        if (shortUrlMap.size() >= shortDomainMaximum) {
            return Response.error("短域名存储已达上限");
        }
        String shortUri;
        if (longUrlMap.containsKey(longUrl)) {
            shortUri = longUrlMap.get(longUrl);
        } else {
            shortUri = ShortUrlUtil.generateShortUrl();
            shortUrlMap.put(shortUri, longUrl);
            longUrlMap.put(longUrl, shortUri);
        }
        String shortUrl = String.format(SHORT_URL_FORMAT, shortDomainHost, shortUri);
        return Response.success(shortUrl);
    }

    public Response<String> getLongUrl(String shortUri) {
        if (StringUtils.isBlank(shortUri)) {
            return Response.error("短域名不可为空");
        }
        String longUrl = shortUrlMap.get(shortUri);
        if (longUrl == null) {
            return Response.error("短域名不存在");
        }
        return Response.success(longUrl);
    }

    private static boolean isValidUrl(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
