package com.scdt.url.service;

import com.scdt.utils.MD5Utils;
import com.scdt.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ShortUrlServiceImpl implements IShortUrlService {
    private Map<String, String> longUrlMap = new ConcurrentHashMap<String, String>();
    private Map<String, String> shortUrlMap = new ConcurrentHashMap<String, String>();
    private UrlValidator urlValidator = new UrlValidator();

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;

    /**
     * 根据长域名创建短域名
     *
     * @param url
     * @return
     */
    @Override
    public String shortUrlCreate(String url) {
        // url有效性验证
        if (!urlValidator.isValid(url) || url.length() > 255) return null;

        String key = MD5Utils.md5(url);
        synchronized (key) {
            if (!longUrlMap.containsKey(key)) {
                boolean loop = true;
                while(loop) {
                    String showUrlCode = RandomUtils.getRandom(4);
                    String showUrl = shortUrlPrefix + showUrlCode;
                    if (!shortUrlMap.containsKey(showUrl)) {
                        longUrlMap.putIfAbsent(key, showUrl);
                        shortUrlMap.putIfAbsent(showUrl, url);
                        loop = false;
                    }
                }
            }
        }
        return longUrlMap.getOrDefault(key, null);
    }

    /**
     * 根据短域名查找长域名
     *
     * @param url
     * @return
     */
    @Override
    public String longUrlFind(String url) {
        return shortUrlMap.getOrDefault(url, null);
    }
}
