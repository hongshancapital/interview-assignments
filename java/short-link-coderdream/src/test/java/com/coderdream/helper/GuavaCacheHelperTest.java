package com.coderdream.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GuavaCacheHelperTest {

    @Resource
    private GuavaCacheHelper guavaCacheHelper;

    @Test
    public void testCache() {
        assertNull(guavaCacheHelper.get(null));
        guavaCacheHelper.put("123", "test");
        guavaCacheHelper.put(null, null);
        guavaCacheHelper.put("abc", null);
        guavaCacheHelper.put("", null);
        assertEquals("test", guavaCacheHelper.get("123"));
    }
}