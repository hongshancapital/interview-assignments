package com.shortlink.util;

import com.shortlink.common.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class ShortUrlCoderTest {
    @Test
    public void testEncode(){
        String shortUrl = ShortUrlEnCoder.encodeLongUrl("http://www.baidu.com");
        log.info("shortUrl: " + shortUrl);
        Assert.assertTrue("xE1vhM4w".equals(shortUrl));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testErrorbytes(){
        byte[] a = new byte[0];
        ShortUrlEnCoder.encode16bytes(a);
    }
}
