package com.assignment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

/**
 * Junit测试
 * @author mrdiyewu@gmail.com
 * @date 2021/10/12 15:21
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ConvertService.class })

public class ConvertServiceTest  {
    private String longUrl = "www.baidu.com";
    private String longUrlOver = "www.baidu.commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm";
    private String shortUrl = "https://goo.gl/e6FFja";
    private String shortUrlNotExsit = "https://goo.gl/e6FFja11111";

    @Autowired
    private ConvertService convertService;

    @Test
    public void DomainLong2Short(){
        assertEquals(shortUrl, convertService.l2s(longUrl));
        assertEquals(shortUrl, convertService.l2s(longUrl));
    }

    @Test
    public void DomainLong2ShortOver(){
        assertNotEquals(shortUrl, convertService.l2s(longUrlOver));
    }


    @Test
    public void DomainShort2Long(){
        assertEquals(longUrl, convertService.s2l(shortUrl));
    }

    @Test
    public void DomainShort2LongNotExist(){
//        assertNotEquals(longUrl, convertService.s2l(shortUrlNotExsit));
    }
}
