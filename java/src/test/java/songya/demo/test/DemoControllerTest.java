package songya.demo.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import songya.demo.Starter;
import songya.demo.controller.DemoController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Starter.class })
public class DemoControllerTest {
    @Autowired
    DemoController demoController;

    @Test
    public void generateAndSaveShortUrlTest(){
        Assert.assertNotNull(demoController.generateAndSaveShortUrl("https://baidu.com"));
    }

    @Test
    public void getUrlByShortUrlTest(){
        Assert.assertNotNull(demoController.getUrlByShortUrl("https://baidu.com"));
    }
}
