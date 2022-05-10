package com.link.service.impl;

import com.link.service.LinkService;
import com.link.utils.ShortUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auth zong_hai@126.com
 * @date 2022-04-16
 * @desc
 */
@Service
public class LinkServiceImpl implements LinkService, InitializingBean {
    /**
     * 用户来缓存数据的。
     */
    private Map<String, String> caches;

    @Override
    public String generateShortLinkByLongLink(String longLink) {
        String shortLink = caches.get(longLink);
        if (!StringUtils.isEmpty(shortLink)) {
            return shortLink;
        }
        String[] encryptData = ShortUtils.generateShort(longLink);
        for (int i = 0; i < encryptData.length; i++) {
            if (caches.containsKey(encryptData[i])) {
                continue;
            }
            shortLink = ShortUtils.shortDomain + encryptData[i];
            String oldValue = caches.putIfAbsent(shortLink, longLink);
            if (StringUtils.isEmpty(oldValue)) {
                break;
            }
        }
        return shortLink;
    }

    @Override
    public String queryLongLinkByShortLink(String shortLink) {
        String longLink = caches.get(shortLink);
        return longLink;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        caches = new ConcurrentHashMap<>();
    }
}
