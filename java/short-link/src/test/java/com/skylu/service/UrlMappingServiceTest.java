package com.skylu.service;

import com.skylu.App;
import com.skylu.constants.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName UrlMappingServiceTest.java
 * @Description 域名接口测试类
 * @createTime 2022年04月22日 17:50:00
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UrlMappingServiceTest {

    @Autowired
    private UrlMappingService urlMappingService;

    @Test
    public void shortStore() {
        String url = "http://www.sina.com";
        urlMappingService.longToShort(url);
        //测试传空字符串
        urlMappingService.longToShort("");
    }

    @Test
    public void batchStore() {
        String url = "http://www.sina.com";
        for (int i = 1; i < LocalCache.MAX + 1000; i++) {
            urlMappingService.longToShort(String.format("%s/%d", url, i));
        }
    }

    @Test
    public void getLongUrl() {
        String url = "http://www.sina.com";
        String code = urlMappingService.longToShort(url);
        urlMappingService.shortToLong(code);
        //测试传空字符串
        urlMappingService.shortToLong("");
    }
}
