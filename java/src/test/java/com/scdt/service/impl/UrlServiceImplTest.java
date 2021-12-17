package com.scdt.service.impl;

import com.scdt.util.LRUCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Class
 *
 * @Author: lenovo
 * @since: 2021-12-16 20:23
 */
public class UrlServiceImplTest {
    @Mock
    LRUCache<String, String> URL_MAP;
    @InjectMocks
    UrlServiceImpl urlServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet() throws Exception {
        when(URL_MAP.get(any())).thenReturn("");
        urlServiceImpl.put("https://blog.csdn.net/jiahao1186/article/details/103764881");
        String result = urlServiceImpl.get("fSub8DDb");
        Assert.assertEquals("https://blog.csdn.net/jiahao1186/article/details/103764881", result);
        String result2 = urlServiceImpl.get("fSub8DD1");
        Assert.assertEquals(result2,"not found!");
    }

    @Test
    public void testPut() throws Exception {
        String result = urlServiceImpl.put("https://blog.csdn.net/jiahao1186/article/details/103764881");
        Assert.assertEquals("fSub8DDb", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme