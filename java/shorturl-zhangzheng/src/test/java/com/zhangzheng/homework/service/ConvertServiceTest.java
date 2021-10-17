package com.zhangzheng.homework.service;

import com.zhangzheng.homework.ApplicationTests;
import com.zhangzheng.homework.entity.UrlMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/10 下午12:57
 */
@Slf4j
public class ConvertServiceTest extends ApplicationTests {
    @Autowired
    private ConvertService convertService;

    @Test(expected = RuntimeException.class)
    public void invalidUrl(){
        String longUrl = "www.adfa.com/werwe/wefa";
        convertService.generate(longUrl);
    }

    @Test(expected = RuntimeException.class)
    public void emptyUrl(){
        String longUrl = "";
        convertService.generate(longUrl);
    }

    @Test
    public void correctConvertAndRevert(){
        String longUrl = "http://www.aaaaa.com/bbb/ccc/dddd";
        String shortKey = convertService.generate(longUrl);
        log.debug("Test:correctConvertAndRevert,shortKey={}",shortKey);
        Assert.assertTrue(!StringUtils.isEmpty(shortKey));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String revertUrl = convertService.revertUrl(shortKey);
        log.debug("Test:correctConvertAndRevert,revertUrl={}",revertUrl);
        Assert.assertTrue(longUrl.equalsIgnoreCase(revertUrl));
    }
    @Test
    public void getUrlMapByShortNull(){
        UrlMap urlMap = convertService.getUrlMapByShort(null);
        Assert.assertNull(urlMap);
    }
    @Test
    public void getUrlMapByShort(){
        String longUrl = "http://www.aaaaa.com/bbb/ccc/dddd";
        String shortKey = convertService.generate(longUrl);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UrlMap urlMap = convertService.getUrlMapByShort(shortKey);
        Assert.assertNotNull(urlMap);
    }
    @Test
    public void getUrlMapByLongNull(){
        UrlMap urlMap = convertService.getUrlMapByLong(null);
        Assert.assertNull(urlMap);
    }
    @Test
    public void getUrlMapByLong(){
        String longUrl = "http://www.aaaaa.com/bbb/ccc/dddd";
        String shortKey = convertService.generate(longUrl);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UrlMap urlMap = convertService.getUrlMapByLong(longUrl);
        Assert.assertNotNull(urlMap);
    }
}
