package com.skylu.controller;

import com.skylu.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName UrlMappingControllerTest.java
 * @Description 接口测试
 * @createTime 2022年04月22日 18:18:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UrlMappingControllerTest {

    @Autowired
    private UrlMappingController controller;

    @Test
    public void shortStore() {
        String url = "http://www.sina.com";
        controller.shortStore(url);
    }

    @Test
    public void getLongUrl() {
        String shortCode = "sdasa1s";
        controller.getLongUrl(shortCode);
    }
}
