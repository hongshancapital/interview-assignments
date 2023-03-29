package com.example.urltransform;

import com.example.urltransform.service.UrlTransformService;
import com.example.urltransform.service.UrlTransformServiceImpl;
import com.example.urltransform.util.UrlTransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
@Slf4j
class UrltransformApplicationTests {

    @Autowired
    private UrlTransformService transformService;
    
    @Test
    void contextLoads() {
    }

    /**
     * 自增id方式获取ShortUrl
     */
    @Test
    public void getShortUrlWithId(){
        UrlTransformUtil.getShortUrlWithId();
    }


    /**
     * save origninal url
     */
    @Test
    public void saveOriginalUrl(){
        for (int i = 0; i < 5; i++) {
            String shortUrl = transformService.saveOriginalUrl("http://www.baidu.com"+i);
            log.info("short url:{}",shortUrl);
        }
    }

    /**
     * 获取 orignal url
     */
    @Test
    public void getOriginalUrl(){
         Set<String> shortSet = UrlTransformServiceImpl.urlCacheMap.keySet();
         shortSet.stream().forEach(url ->{
             String longUrl = transformService.getOriginalUrl(url);
             log.info("shortUrl:{},longUrl:{}",url,longUrl);
         });
    }
}
