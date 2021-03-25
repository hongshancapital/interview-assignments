package com.wjup.shorturl.service;

import com.wjup.shorturl.ShorturlApplication;
import com.wjup.shorturl.service.serviceimpl.UrlServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ShorturlApplication.class})
@Slf4j
public class UrlServiceTest {

    @Before
    public void init() {
        log.info("测试开始。。。");
    }

    @After
    public void after(){
        log.info("测试结束。。。");
    }

    @MockBean
    private UrlServiceImpl urlServiceImpl;

    @SuppressWarnings("unchecked")
    @Test(expected = NullPointerException.class)
    public void testMockBean() {

        //
//        Object mock = BDDMockito.given(urlServiceImpl.createShortUrl("", null, null)).getMock();
        String shortUrl = urlServiceImpl.createShortUrl("", null, null);
        Assert.assertNotNull(shortUrl);
    }


}
