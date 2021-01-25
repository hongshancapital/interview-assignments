package com.luman.shorturl.service;

import com.luman.shorturl.api.service.ShortUrlService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShortUrlServiceTest {
    @Autowired
    ShortUrlService shortUrlService;
    @Value("${domain}")
    private String domain;
    @Test
    public void testGen(){
        String TEST_URL="https://www.baidu.com";
        String code = shortUrlService.gen(TEST_URL,null);
        TestCase.assertNotNull(code);
        code = code.replace(domain,"");
        String url = shortUrlService.getUrl(code);
        TestCase.assertNotNull(url);
        TestCase.assertEquals(url,TEST_URL);
    }

}
