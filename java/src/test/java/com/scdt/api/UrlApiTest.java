package com.scdt.api;

import com.scdt.service.impl.UrlServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Class
 *
 * @Author: lenovo
 * @since: 2021-12-16 20:26
 */
public class UrlApiTest {
    @Mock
    UrlServiceImpl urlService;
    @InjectMocks
    UrlApi urlApi;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPut() throws Exception {
        when(urlService.put(anyString())).thenReturn("putResponse");

        String result = urlApi.put("longUrl");
        Assert.assertEquals("putResponse", result);
    }

    @Test
    public void testGet() throws Exception {
        when(urlService.get(anyString())).thenReturn("getResponse");
        urlApi.put("longUrl");
        String result = urlApi.get("y9XL49fa");
        Assert.assertEquals("getResponse", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme