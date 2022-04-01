package com.zz.store;

import com.zz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class ShortUrlStoreTest extends BaseTest {
    @Autowired
    private ShortUrlStore shortUrlStore;

    @Test
    public void storeMapping() {
        shortUrlStore.storeMapping("123", "123");
    }

    @Test
    public void getUrlByShortCode() {
        shortUrlStore.storeMapping("456", "456");
        String str = shortUrlStore.getUrlByShortCode("456");
        Assert.assertEquals("456", str);
    }

    @Test
    public void getShortCodeByUrl() {
        shortUrlStore.storeMapping("789", "789");
        String str = shortUrlStore.getUrlByShortCode("789");
        Assert.assertEquals("789", str);
    }
}