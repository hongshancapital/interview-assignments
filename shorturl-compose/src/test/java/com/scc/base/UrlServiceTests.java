package com.scc.base;

import com.scc.base.service.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author renyunyi
 * @date 2022/4/26 11:45 PM
 * @description UrlService test
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlComposeApplication.class)
public class UrlServiceTests {

    @Autowired
    private UrlService urlService;

    @Test
    public void testGetShortUrlFrom(){
        String shortUrl = urlService.getShortUrlFrom("/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvxx");
        assert StringUtils.equals("1iM", shortUrl);
    }

    @Test
    public void testGetLongUrlFrom(){
        String longUrl = urlService.getLongUrlFrom("1iM");
        assert StringUtils.equals("/abscdesfshelelejcle/52062-6-56365352/7759dwmfwvxx", longUrl);
    }

}
