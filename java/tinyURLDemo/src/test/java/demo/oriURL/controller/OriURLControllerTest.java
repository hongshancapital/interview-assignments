package demo.oriURL.controller;

import demo.TinyUrlDemoApplication;
import demo.common.result.Result;
import demo.oriURL.dto.OriURLRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TinyUrlDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OriURLControllerTest {
    @Autowired
    private OriURLController oriURLController;

    @Test
    public void transUrl() {
        OriURLRequest oriURLRequest = new OriURLRequest();

        String oriURL = "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate";
        oriURLRequest.setOriURL(oriURL);
        Result<String> result = this.oriURLController.transUrl(oriURLRequest);

        assertEquals(200, result.getCode());
        assertEquals("success", result.getMsg());
        assertNotNull(result.getData());


        oriURL = "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate";
        oriURLRequest.setOriURL(oriURL);
        result = this.oriURLController.transUrl(oriURLRequest);

        assertEquals(200, result.getCode());
        assertEquals("success", result.getMsg());
        assertNotNull(result.getData());


        oriURL = "htps://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate";
        oriURLRequest.setOriURL(oriURL);
        result = this.oriURLController.transUrl(oriURLRequest);

        assertEquals(200, result.getCode());
        assertEquals("入参为非法 URL！", result.getMsg());
        assertNull(result.getData());


        oriURL = "";
        oriURLRequest.setOriURL(oriURL);
        result = this.oriURLController.transUrl(oriURLRequest);

        assertEquals(200, result.getCode());
        assertEquals("入参为非法 URL！", result.getMsg());
        assertNull(result.getData());
        System.out.println(result.toString());
    }
}