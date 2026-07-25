package com.scdt.shortlink.core.engine;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class ShortLinkEngineTest {

    private static final int COUNT = 8;

    @Test
    public void testCreateShortLink() {
        String shortLink = ShortLinkEngine.createShortLink(COUNT);
        Assert.assertEquals(shortLink.length(), COUNT);
    }

}