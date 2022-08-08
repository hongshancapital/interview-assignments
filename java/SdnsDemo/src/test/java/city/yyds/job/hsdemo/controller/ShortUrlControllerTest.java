package city.yyds.job.hsdemo.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class ShortUrlControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlControllerTest.class);
    @Autowired
    private ShortUrlController shortUrlController;
    @Test
    void getShortUrlCase() {
        String url ="http://www.baidu.com";
        String result = shortUrlController.getShortUrl(url);
        log.info("为url：{}生成短码是: {}", url,result);
        Assert.assertNotNull(result);
    }

}
