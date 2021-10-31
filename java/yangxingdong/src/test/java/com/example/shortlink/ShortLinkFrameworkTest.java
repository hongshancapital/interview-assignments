package com.example.shortlink;

import com.example.shortlink.service.ShortLongLinkService;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
public class ShortLinkFrameworkTest extends BaseTest{

    @Autowired
    private ShortLongLinkService service;


    /**
     * data : short link : http://www.baidu.com/fhdashfdjksafds
     * result :
     *          passed
     */
    @Test
    public void acquireShortLinkTest() {
        String longLink = "http://www.baidu.com/fhdashfdjksafds" ;
        result = service.acquireShortLink(longLink);
    }



    /**
     * target  : test function acquireShortLink ,acquireLongLink
     * result :
     *          passed
     */
    @Test
    public void acquireLongLinkTest() {
        String longLink = "http://www.baidu.com/fhdashfdjksafds" ;
        String shortLink = service.acquireShortLink(longLink);


        result = service.acquireLongLink(shortLink);


        Assert.assertEquals(longLink, result);
    }

}
