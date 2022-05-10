package com.link.test;

import com.link.LinkApplication;
import com.link.service.LinkService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @auth zong_hai@126.com
 * @date 2022-04-16
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkApplication.class)
public class TestLinkService {

    @Autowired
    private LinkService linkService;

    @Test
    public void testGenerateShortLinkByLongLink() {
        String longLink = "https://wenku.baidu.com/view/5e6b5028bdd5b9f3f90f76c66137ee06eff94ec8.html?id=1234";
        String link = linkService.generateShortLinkByLongLink(longLink);
        Assert.assertNotNull(link);
        Assert.assertTrue(link, link.length() <= 8);
        link = linkService.generateShortLinkByLongLink(longLink);
        Assert.assertNotNull(link);
        Assert.assertTrue(link, link.length() <= 8);

    }

    @Test
    public void testQueryLongLinkByShortLink() {
        String longLink = "https://wenku.baidu.com/view/5e6b5028bdd5b9f3f90f76c66137ee06eff94ec8.html?id=1234";
        String shortLink = linkService.generateShortLinkByLongLink(longLink);
        String cachedLink = linkService.queryLongLinkByShortLink(shortLink);
        Assert.assertNotNull(shortLink);
        Assert.assertEquals(longLink, cachedLink);
    }
}
