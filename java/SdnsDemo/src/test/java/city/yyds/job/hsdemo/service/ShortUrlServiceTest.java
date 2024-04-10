package city.yyds.job.hsdemo.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class ShortUrlServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ShortUrlServiceTest.class);
    @Autowired
    ShortUrlService shortUrlService;
    @Test
    void illegalUrlCase() {
        String url ="w$%.baidu.com";
        String result = shortUrlService.getShortUrl(url);
        log.info("为url：{}生成短码是: {}", url,result);
        Assert.assertNull(result);
    }
    @Test
    void getShortUrlCase1() {
        String url ="http://www.baidu.com";
        String result = shortUrlService.getShortUrl(url);
        log.info("为url：{}生成短码是: {}", url,result);
        Assert.assertNotNull(result);
    }
    @Test
    void getShortUrlCase2() {
        String url ="https://m.gmw.cn/2021-11/07/content_35292677.htm";
        String result = shortUrlService.getShortUrl(url);
        log.info("为url：{}生成短码是: {}", url,result);
        Assert.assertNotNull(result);
    }
    @Test
    void getShortUrlCase3() {
        String url ="";
        String result = shortUrlService.getShortUrl(url);
        log.info("为url：{}生成短码是: {}", url,result);
        Assert.assertNull(result);
    }

    @Test
    void getShortUrlCase4() {
        for(int i=0; i<4; i++){
            String url =new StringBuffer("http://www.yyds.city/").append(i).toString();
            String result = shortUrlService.getShortUrl(url);
            log.info("为url：{}生成短码是: {}", url,result);
            Assert.assertNotNull(result);
        }
    }

    @Test
    void getShortUrlCase5() {
        String url ="http://www.baidu.com";
        String result = shortUrlService.getShortUrl(url);
        log.info("为url：{}生成短码是: {}", url,result);
        Assert.assertNotNull(result);
    }
}
