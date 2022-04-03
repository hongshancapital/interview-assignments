package com.findme.url;

import com.findme.url.service.TransformUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    TransformUrl transformUrl;

    @Test
    public void longTransformShortUrl() {

        System.out.println(transformUrl.longTransformShortUrl("12"));
    }

    @Test
    public void shortTransformLongUrl() {
        transformUrl.shortTransformLongUrl("12");
    }

}
