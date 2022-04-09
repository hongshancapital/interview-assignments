package com.qinghaihu.shorturl.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceTest {

    @Autowired
    ShortUrlService shortUrlService;

    /**
     * 测试正常生成短链接，且生成的短链接长度不大于8字符
     */
    @Test
    public void testTransferToShortUrlLength() {
        String shortUrl = shortUrlService.transferToShortUrl("^7&*4343434hfjjjfjf/fjewuAf");
        assertTrue(shortUrl.length() <= 8 );
    }

    /**
     * 测试生成的短链接具有幂等性
     */
    @Test
    public void testTransferToShortUrlPower() {
        String longUrlInput = "^7&*4343434hfjjjfjf/fjewuAf";
        String shortUrl1 = shortUrlService.transferToShortUrl(longUrlInput);
        String shortUrl2 = shortUrlService.transferToShortUrl(longUrlInput);
        assertEquals(shortUrl1,shortUrl2);
    }


    /**
     * 测试待转换长链接为空时主动抛出异常
     */
    @Test(expected = RuntimeException.class)
    public void testThrowExWhenShortUrlIsEmpty() {
        shortUrlService.transferToShortUrl("");
    }


    /**
     * 测试Hash冲突 涉及静态方法和及动态绑定类，暂时找不到可行mock方法
     */
    @Test()
    public void testHashConflict() throws Exception {

        HashCode code = HashCode.fromInt(74774744);
        //PowerMockito.spy(HashFunction.class);
        //PowerMockito.when(HashFunction.class, "hashString",new String("hello"),StandardCharsets.UTF_8).thenReturn(code);
        HashFunction hashFunction =  Mockito.mock(HashFunction.class);
        PowerMockito.when(hashFunction.hashString(Mockito.any(CharSequence.class), eq(StandardCharsets.UTF_8))).thenReturn(code);
        String firstResult = shortUrlService.transferToShortUrl("first");
        String secodResult = shortUrlService.transferToShortUrl("second");
        assertNotEquals(firstResult,secodResult); //hash冲突，两次的转换结果应不一样
    }



    /**
     * 测试短链接不存在时，转换成长链接会抛出异常
     */
    @Test(expected = RuntimeException.class)
    public void testShortUrlNotExist() {
         shortUrlService.transferToLongUrl("h368ds");
    }

    /**
     * 测试短链接正常转换为长链接
     */
    @Test
    public void transferToLongUrl() {
        String longUrlInput = "^7&*4343434hfjjjfjf/fjewuAf";
        String shortUrl = shortUrlService.transferToShortUrl(longUrlInput);
        String transferResult = shortUrlService.transferToLongUrl(shortUrl);
        assertEquals(longUrlInput,transferResult);
    }

}