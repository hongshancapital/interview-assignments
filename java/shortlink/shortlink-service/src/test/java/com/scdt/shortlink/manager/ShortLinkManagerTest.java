package com.scdt.shortlink.manager;

import com.scdt.shortlink.core.cache.ICache;
import com.scdt.shortlink.core.engine.ShortLinkEngine;
import com.scdt.shortlink.exception.CreateShortLinkException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * 短链服务manager层单元测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ShortLinkEngine.class)
public class ShortLinkManagerTest {

    @InjectMocks
    private ShortLinkManager shortLinkManager;

    @Mock
    private ICache cache;

    /**
     * 长链
     */
    private static final String ORIGINAL_LINK = "https://github.com/scdt-china/interview-assignments/tree/master/java";

    /**
     * 短链
     */
    private static final String SHORT_LINK = "PwJDwsXy";

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ShortLinkEngine.class);
        PowerMockito.when(ShortLinkEngine.createShortLink(Mockito.anyInt())).thenReturn(SHORT_LINK);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateShortLink() {
        PowerMockito.doNothing().when(cache).put(Mockito.anyString(), Mockito.anyString());
        PowerMockito.when(cache.get(Mockito.anyString())).thenReturn("");

        String shortLink = shortLinkManager.createShortLink(ORIGINAL_LINK);
        Assert.assertEquals(shortLink, SHORT_LINK);

        //异常测试
        PowerMockito.when(cache.get(Mockito.anyString())).thenReturn(SHORT_LINK);
        Assert.assertThrows(CreateShortLinkException.class, () -> shortLinkManager.createShortLink(ORIGINAL_LINK));
    }

    @Test
    public void testGetOriginalLink() {
        PowerMockito.when(cache.get(Mockito.anyString())).thenReturn(ORIGINAL_LINK);
        String originalLink = shortLinkManager.getOriginalLink(SHORT_LINK);
        Assert.assertEquals(originalLink, ORIGINAL_LINK);
    }
}