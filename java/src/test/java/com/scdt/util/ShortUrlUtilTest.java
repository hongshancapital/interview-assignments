package com.scdt.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.MessageDigest;

/**
 * Class
 *
 * @Author: lenovo
 * @since: 2021-12-16 20:26
 */
public class ShortUrlUtilTest {
    @Mock
    MessageDigest md5;
    @InjectMocks
    ShortUrlUtil shortUrlUtil;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMd5HexStr() throws Exception {
        String result ="";
        try{
            ShortUrlUtil.getMd5HexStr(null);
        }catch (Throwable e){

        }
        try{
            ShortUrlUtil.getMd5HexStr("");
        }catch (Throwable e){

        }
        result = ShortUrlUtil.getMd5HexStr("url");
        Assert.assertNotNull(result);
    }

    @Test
    public void testParseByte2HexStr() throws Exception {
        String result = ShortUrlUtil.parseByte2HexStr(new byte[]{(byte) 0,(byte) 1,(byte) 2});
        Assert.assertEquals("000102", result);
    }

    @Test
    public void testShortUrl() throws Exception {
        String result = ShortUrlUtil.shortUrl("url");
        Assert.assertEquals("O8Hyvn8b", result);
    }

    @Test
    public void testShortUrls() throws Exception {
        String[] result = ShortUrlUtil.shortUrls("url");
        Assert.assertArrayEquals(new String[]{"O8Hyvn8b",
                "91zTOWna","zjSK49ba","KCXXDrrb"}, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme