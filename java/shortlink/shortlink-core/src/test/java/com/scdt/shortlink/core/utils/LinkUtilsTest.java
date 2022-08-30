package com.scdt.shortlink.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * url工具测试类
 *
 * @Author tzf
 * @Date 2022/4/28
 */
@RunWith(PowerMockRunner.class)
public class LinkUtilsTest {
    private static final String BASE_URL = "https://www.123.com/asdf";

    private LinkUtils linkUtils = new LinkUtils();

    @Test
    public void testRemoveParam() {
        String url = BASE_URL + "?param=xyg";
        Assert.assertEquals(LinkUtils.removeParam(url), BASE_URL);

        url = StringUtils.EMPTY;
        Assert.assertEquals(LinkUtils.removeParam(url), StringUtils.EMPTY);

        url = BASE_URL;
        Assert.assertEquals(LinkUtils.removeParam(url), BASE_URL);
    }

    @Test
    public void testCheckLinkRule() {
        boolean result = LinkUtils.checkLinkRule(BASE_URL);
        Assert.assertTrue(result);

        result = LinkUtils.checkLinkRule("test");
        Assert.assertFalse(result);

        result = LinkUtils.checkLinkRule("http://www.123.com/asdf");
        Assert.assertTrue(result);
    }
}
