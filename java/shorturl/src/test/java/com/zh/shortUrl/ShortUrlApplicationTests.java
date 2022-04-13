package com.zh.shortUrl;

import com.google.gson.Gson;
import com.zh.shortUrl.common.BaseResponse;
import com.zh.shortUrl.controller.ShortUrlController;
import com.zh.shortUrl.dto.ShortUrlCommonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlApplicationTests {
    @Autowired
    ShortUrlController shortUrlController;

    /**
     * 常规获取短链操作
     */
    @Test
    public void testGetShortUrl(){
        ShortUrlCommonDto dto = new ShortUrlCommonDto();
        dto.setLongUrl("http://www.baidu.com");
        BaseResponse response =shortUrlController.getShortUrl(dto);
        System.out.println(new Gson().toJson(response));
    }

    /**
     * 重复获取短链
     */
    @Test
    public void testGetShortUrl1(){
        ShortUrlCommonDto dto = new ShortUrlCommonDto();
        dto.setLongUrl("http://www.baidu.com");
        BaseResponse response =shortUrlController.getShortUrl(dto);
        System.out.println(new Gson().toJson(response));
        BaseResponse response1 =shortUrlController.getShortUrl(dto);
        System.out.println(new Gson().toJson(response1));
    }

    /**
     * 正常获取长链
     */
    @Test
    public void testGetLongUrl(){
        ShortUrlCommonDto dto = new ShortUrlCommonDto();
        dto.setLongUrl("http://www.baidu.com");
        BaseResponse<String> response =shortUrlController.getShortUrl(dto);
        System.out.println(new Gson().toJson(response));
        ShortUrlCommonDto dto1 = new ShortUrlCommonDto();
        dto1.setShortUrl(response.getData());
        BaseResponse response1 =shortUrlController.getLongUrl(dto1);
        System.out.println(new Gson().toJson(response1));
    }

    /**
     * 获取不存在的长链
     */
    @Test
    public void testGetLongUrl1(){
        ShortUrlCommonDto dto = new ShortUrlCommonDto();
        dto.setShortUrl("http://www.baidu.com1");
        BaseResponse<String> response =shortUrlController.getLongUrl(dto);
        System.out.println(new Gson().toJson(response));
    }

}
