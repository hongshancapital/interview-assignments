package com.shorturl.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerVersionCacheTest {

    @Autowired
    private ServerVersionCache cache;

    @Autowired
    private StringRedisTemplate template;

    @Before
    public void prepareData() {
        template.opsForSet().add(ServerVersionCache.SERVER_VERSION_IN_USE, "0", "1", "2");
    }

    @After
    public void removeData() {
        Set<String> versions = template.opsForSet().members(ServerVersionCache.SERVER_VERSION_IN_USE);
        template.opsForSet().remove(ServerVersionCache.SERVER_VERSION_IN_USE, versions.toArray());
    }

    @Test
    public void readInUseCache() throws Exception {
        Set<String> versions = cache.readInUseCache();

        assertTrue(versions.contains("0"));
        assertTrue(versions.contains("1"));
        assertTrue(versions.contains("2"));
    }

    @Test
    public void appendInUseCache() throws Exception {
        Long version = 3L;
        cache.addInUseCache(version);

        Set<String> versions = template.opsForSet().members(ServerVersionCache.SERVER_VERSION_IN_USE);
        assertTrue(versions.contains(version.toString()));
    }

    @Test
    public void removeVersion() throws Exception {
        Long version = 2L;
        cache.removeVersion(version);

        Set<String> versions = template.opsForSet().members(ServerVersionCache.SERVER_VERSION_IN_USE);
        assertFalse(versions.contains(version));
    }
}