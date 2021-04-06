package com.d00216118.demo.service;

import com.d00216118.demo.model.InfoUrl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 10:15 上午 2021/4/5
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceTest {


    @Autowired
    private UrlServiceImpl urlService;

    @Autowired
    private SecurityServiceImpl securityService;




    @Test
    public void TestAll() {
        String url = "https://github.com/jade75/interview-assignments";
        InfoUrl infoUrl = new InfoUrl();
        infoUrl.setUrl(url);
        infoUrl.setMd5Url(DigestUtils.md5DigestAsHex(url.getBytes()));
        infoUrl.setUserId(new Long("1"));
        infoUrl.setCreatedStamp(System.currentTimeMillis());

        InfoUrl infoUrl2 = urlService.convertToTinyUrl(infoUrl);
        boolean b = urlService.checkUrlExist(url,1L);

        Assert.assertNotNull(urlService.getUrlByTinyUrl(infoUrl2.getTinyUrl(),infoUrl2.getUserId()));

        Assert.assertTrue(b);
    }


    @Test
    public void securityTest() {
        Assert.assertTrue(securityService.checkUrl("http://www.uu.com"));
        String token = securityService.createToken("test", 30 * 60 * 1000);
        Assert.assertNotNull(token);

        String r = securityService.getSubject(token);
        Assert.assertEquals("success", "test", r);
    }
}
