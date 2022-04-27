package com.scdt.service;

import com.scdt.ShortLinkApplication;
import com.scdt.exception.BusinessException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * LinkServiceTest
 *
 * @author weixiao
 * @date 2022-04-27 08:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShortLinkApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkServiceTest {
    @Autowired
    private LinkService linkService;

    @Test
    public void testCreateShortLink() {
        Assert.assertNotNull(linkService.createShortLink("www.baidu.com"));
    }

    @Test
    public void testGetLongLink() {
        Assert.assertNotNull(linkService.getLongLink("00000000"));
    }

    @Test(expected = BusinessException.class)
    public void testGetLongLinkException() {
        Assert.assertNotNull(linkService.getLongLink("000000"));
    }
}
