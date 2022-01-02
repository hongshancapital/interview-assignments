package com.oldnoop.shortlink.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShortLinkServiceTest {

    @Autowired
    private ShortLinkService service;

    @Test
    public void test() {
    	String longLink = "https://github.com/scdt-china/interview-assignments";
        List<String> shortLink = service.create(Lists.newArrayList(longLink));
        String linkValue = service.search(shortLink.get(0));
        Assert.assertEquals(longLink, linkValue);
        List<String>  shortLink2 = service.create(Lists.newArrayList(longLink));
        String linkValue2 = service.search(shortLink2.get(0));
        Assert.assertEquals(longLink, linkValue2);
    }

}