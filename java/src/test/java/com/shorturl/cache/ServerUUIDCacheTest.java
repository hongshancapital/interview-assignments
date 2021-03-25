package com.shorturl.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerUUIDCacheTest {

    @Autowired
    private ServerUUIDCache cache;

    @Test
    public void setServerUUID() throws Exception {
        String uuid = UUID.randomUUID().toString();
        cache.setServerUUID(uuid);
    }

    @Test
    public void getServerUUID() throws Exception {
        System.out.println(cache.getServerUUID());
    }

    @Test
    public void refreshExpiration() throws Exception {
    }
}