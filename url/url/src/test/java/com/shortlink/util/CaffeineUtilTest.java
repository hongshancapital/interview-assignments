package com.shortlink.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaffeineUtilTest {

    @Test
    public void testCache() {
        Assert.assertNull(CaffeineUtil.get(null));
        CaffeineUtil.put("a", "test");
        Assert.assertEquals("test", CaffeineUtil.get("a"));
        CaffeineUtil.del("a");
        Assert.assertNull(CaffeineUtil.get("a"));
    }
}
