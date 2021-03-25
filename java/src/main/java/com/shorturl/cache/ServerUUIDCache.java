package com.shorturl.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by ruohanpan on 21/3/22.
 */
@Component
public class ServerUUIDCache {

    private static final String SERVER_UUID = "server_uuid";

    private static final long KEY_EXPIRE = 10 * 60 * 1000;

    @Autowired
    private StringRedisTemplate template;

    public void setServerUUID(String uuid) {
        template.opsForValue().set(SERVER_UUID, uuid, KEY_EXPIRE, TimeUnit.MILLISECONDS);
    }

    public String getServerUUID() {
        return template.opsForValue().get(SERVER_UUID);
    }

    public void refreshExpiration() {
        template.expire(SERVER_UUID, KEY_EXPIRE, TimeUnit.MILLISECONDS);
    }
}
