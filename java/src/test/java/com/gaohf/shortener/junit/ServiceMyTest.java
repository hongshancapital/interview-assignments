package com.gaohf.shortener.junit;

import com.gaohf.shortener.commons.response.ResponseResult;
import com.gaohf.shortener.service.IUrlShortenerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

/**
 * @author: Gaohf
 * @create: 2021-05-11 19:17:47
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceMyTest {

    @Autowired
    IUrlShortenerService urlShortenerService;

    @Test
    public void create(){
        String  curl = "https://www.baidu.com";
        HashSet<String> stringHashSet = new HashSet<>();
        String after = null;
        for(int i=0;i<100;i++){
           after =  curl+i;
            ResponseResult responseResult = urlShortenerService.create(after);
        }

        ResponseResult shortUrlFromLongUrl1 = urlShortenerService.create("https://www.baidu.com/0");

        String url = null;
        ResponseResult gurl = urlShortenerService.create(url);
    }

    @Test
    public void getUrl(){
        ResponseResult responseResult = urlShortenerService.create("https://www.baidu.com");

        ResponseResult url = urlShortenerService.getUrl(responseResult.getCode().toString());
        System.out.println(url.getData());
    }
}
