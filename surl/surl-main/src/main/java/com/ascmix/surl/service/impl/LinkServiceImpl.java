package com.ascmix.surl.service.impl;

import com.ascmix.surl.service.LinkService;
import com.ascmix.surl.utils.KeyGenerator;
import com.ascmix.surl.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private CassandraTemplate cassandra;

    @Override
    public String shorten(String url) {
        String key = KeyGenerator.gen();
        Link link = new Link(key, url);
        cassandra.insert(link);
        redis.opsForValue().set(key, url);
        return key;
    }

    @Override
    public String get(String key) {
        String url = redis.opsForValue().get(key);
        if (url == null) {
            Link link = cassandra.selectOneById(key, Link.class);
            if (link == null) {
                redis.opsForValue().set(key, "");
                return null;
            }
            url = link.getOriginalUrl();
            redis.opsForValue().set(key, url);
        }
        return url;
    }

    @Override
    public boolean delete(String key) {
        boolean result = cassandra.deleteById(key, Link.class);
        if (result) {
            redis.opsForValue().setIfPresent(key, "", Duration.ofHours(1));
        }
        return result;
    }
}
