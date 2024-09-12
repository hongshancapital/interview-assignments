package com.scdt.shortlink.service.impl;

import com.scdt.shortlink.cache.LinkCache;
import com.scdt.shortlink.cache.LinkLRUCache;
import com.scdt.shortlink.service.Link8Service;
import com.scdt.shortlink.util.StringUtil;
import com.scdt.shortlink.util.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class Link8ServiceImpl implements Link8Service {
    private final AtomicLong LINK_ID = new AtomicLong(0);

    @Autowired
    private LinkCache cache;

    @Override
    public String createLink(String url) {
        long id = LINK_ID.incrementAndGet();
        String key = Transformer.transform8(id);
        if (StringUtil.isNotEmpty(key))
            cache.save(key, url);

        return key;
    }

    @Override
    public String getUrl(String key) {
        //防止缓存穿透，无效id直接过滤掉
        if (!Transformer.validKey(key) || !validKey(key))
            return null;

        return cache.getUrl(key);
    }

    private boolean validKey(String key) {
        return Transformer.getId(key) <= LINK_ID.get();
    }
}
