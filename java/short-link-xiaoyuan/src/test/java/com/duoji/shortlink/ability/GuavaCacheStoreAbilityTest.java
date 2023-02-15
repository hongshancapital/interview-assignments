package com.duoji.shortlink.ability;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 20:38:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GuavaCacheStoreAbilityTest {

    @Resource
    private GuavaCacheStoreAbility guavaCacheStoreAbility;

    @Test
    public void testCache() {
        assertNull(guavaCacheStoreAbility.get(null));
        guavaCacheStoreAbility.put("123", "test");
        assertEquals("test", guavaCacheStoreAbility.get("123"));
    }
}
