package demo.curURL.controller;

import demo.TinyUrlDemoApplication;
import demo.common.result.Result;
import demo.curURL.dto.CurURLRequest;
import demo.oriURL.controller.OriURLController;
import demo.oriURL.dto.OriURLRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TinyUrlDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CurURLControllerTest {
    @Autowired
    private CurURLController curURLController;
    @Autowired
    private OriURLController oriURLController;

    @Test
    public void findURL() {
        OriURLRequest oriURLRequest = new OriURLRequest();

        String oriURL = "https://space.bilibili.com/23947287/video?tid=0&page=1&keyword=&order=pubdate";
        oriURLRequest.setOriURL(oriURL);
        Result<String> result1 = this.oriURLController.transUrl(oriURLRequest);


        CurURLRequest curURLRequest = new CurURLRequest();

        String curURL = "https://t.cn/miH";
        curURLRequest.setCurURL(curURL);
        Result<String> result2 = this.curURLController.findURL(curURLRequest);

        assertEquals(200, result2.getCode());
        assertEquals("success", result2.getMsg());
        assertEquals(oriURL, result2.getData());


        curURL = "htps://t.cn/miH";
        curURLRequest.setCurURL(curURL);
        result2 = this.curURLController.findURL(curURLRequest);

        assertEquals(200, result2.getCode());
        assertEquals("入参为非法 URL！", result2.getMsg());
        assertNull(result2.getData());


        curURL = "https://t.cn/miHfv3G5c";
        curURLRequest.setCurURL(curURL);
        result2 = this.curURLController.findURL(curURLRequest);

        assertEquals(200, result2.getCode());
        assertEquals("入参为非法 URL！", result2.getMsg());
        assertNull(result2.getData());


        curURL = "https://t.cn/miI";
        curURLRequest.setCurURL(curURL);
        result2 = this.curURLController.findURL(curURLRequest);

        assertEquals(200, result2.getCode());
        assertEquals("success", result2.getMsg());
        assertEquals("", result2.getData());
    }
}