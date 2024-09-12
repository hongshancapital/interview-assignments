package com.shortlink.common;

import com.shortlink.ShortLinkApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShortLinkApplication.class)
@Slf4j
public class CacheTest {
    @Autowired
    private ShortLinkCache cache;

    @Test
    public void testUseCache() throws Exception{
        cache.getCache().put("key1", "value1");
        String value = cache.getCache().get("key1");
        Assert.assertTrue("value1".equals(value));
    }
}
