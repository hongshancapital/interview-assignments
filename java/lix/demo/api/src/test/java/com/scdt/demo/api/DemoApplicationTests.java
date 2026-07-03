package com.scdt.demo.api;

import com.scdt.demo.api.controller.ShortURLController;
import com.scdt.demo.common.web.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private ShortURLController shortURLController;

    @Test
    public void test() {
        assertNotNull(shortURLController.shortenUrl("https:????sdfsf"));
        Response<String> response = shortURLController.shortenUrl("https://tinyurl.com/app/myurls");
        assertNotNull(response);
        assertNotNull(shortURLController.getOriginUrl(response.getData()));
        assertNotNull(shortURLController.getOriginUrl("fsD22j"));
    }

}
