package com.skyscreen.urldemo.service.impl;


import com.skyscreen.urldemo.cache.MapCache;
import com.skyscreen.urldemo.controller.UrlController;
import com.skyscreen.urldemo.service.ConvertUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConvertUrlServiceImplTest {

    @InjectMocks
    ConvertUrlServiceImpl convertUrlService;

    @Test
    public void convertToShortUrl() {

       Assertions.assertEquals("",convertUrlService.convertToShortUrl(""));


       MapCache.map.put("abcdefgh","http://www.baidu.com");
       Assertions.assertEquals("http://c.cn/abcdefgh",convertUrlService.convertToShortUrl("http://www.baidu.com"));

        Assertions.assertNotNull(convertUrlService.convertToShortUrl("http://www.baidu.com/1"));


    }

    @Test
    public void convertToLongUrl(){
        Assertions.assertEquals("invalid url",convertUrlService.convertToLongUrl(""));
        MapCache.map.put("abcdefgh","http://www.baidu.com");
        Assertions.assertEquals("http://www.baidu.com",convertUrlService.convertToLongUrl("http://c.cn/abcdefgh"));

    }
}
