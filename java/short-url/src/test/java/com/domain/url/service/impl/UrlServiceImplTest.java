package com.domain.url.service.impl;

import com.domain.url.exception.ServiceException;
import com.domain.url.service.UrlService;
import com.domain.url.web.data.UrlReq;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlServiceImplTest {
    private static final String ORIGIN_URL = "https://image.baidu.com/user/logininfo?src=pc&page=searchresult&time=1637927056021";
    private static final String SHORT_URL = "https://short.com/2Bi";

    @Autowired
    private UrlService urlService;

    @Test
    public void test1Shorten() throws ServiceException {
        Assert.assertNotNull(this.urlService.shorten(UrlReq.builder().url(ORIGIN_URL).build()));
    }

    @Test
    public void test2Original() throws ServiceException {
        Assertions.assertEquals(ORIGIN_URL, this.urlService.original(SHORT_URL));
    }
}