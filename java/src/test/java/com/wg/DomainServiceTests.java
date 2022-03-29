package com.wg;

import com.wg.service.DomainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainServiceTests {

    @Autowired
    private DomainService domainService;

    @Test
    public void getShortUrl(){
        String shortUrl = domainService.getShortUrl("http://www.baidu.com");
        Assert.assertEquals("长链接", "https://test:9999/03c396b5", shortUrl);
    }

    @Test
    public void getRealUrl(){
        String realUrl = domainService.getRealUrl("https://test:9999/03c396b5");
        Assert.assertEquals("长链接", "http://www.baidu.com", realUrl);
    }

}
