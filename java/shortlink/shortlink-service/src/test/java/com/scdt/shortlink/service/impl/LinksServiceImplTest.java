package com.scdt.shortlink.service.impl;

import com.scdt.shortlink.client.domain.BaseErrorCode;
import com.scdt.shortlink.client.domain.ShortLinkErrorCode;
import com.scdt.shortlink.client.dto.Result;
import com.scdt.shortlink.manager.ShortLinkManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * 链接服务单元测试类
 */
@RunWith(PowerMockRunner.class)
public class LinksServiceImplTest {

    @InjectMocks
    private LinksServiceImpl linksService;

    @Mock
    private ShortLinkManager shortLinkManager;

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
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetShortLink() {
        Mockito.when(shortLinkManager.createShortLink(Mockito.anyString())).thenReturn(SHORT_LINK);

        Result<String> result = linksService.getShortLink(ORIGINAL_LINK);
        Assert.assertEquals(result.getModel(), SHORT_LINK);

        result = linksService.getShortLink("");
        Assert.assertEquals(result.getErrorCode(), ShortLinkErrorCode.ORIGINAL_LINK_EMPTY.getErrorCode());

        result = linksService.getShortLink("http://github.com");
        Assert.assertEquals(result.getModel(), SHORT_LINK);

        Mockito.when(shortLinkManager.createShortLink(Mockito.anyString())).thenThrow(new RuntimeException());
        result = linksService.getShortLink(ORIGINAL_LINK);
        Assert.assertEquals(result.getErrorCode(), BaseErrorCode.SYSTEM_ERROR.getErrorCode());

    }

    @Test
    public void testGetOriginalLink() {
        Mockito.when(shortLinkManager.getOriginalLink(Mockito.anyString())).thenReturn(ORIGINAL_LINK);

        Result<String> result = linksService.getOriginalLink(SHORT_LINK);
        Assert.assertEquals(result.getModel(), ORIGINAL_LINK);

        result = linksService.getOriginalLink("");
        Assert.assertEquals(result.getErrorCode(), ShortLinkErrorCode.SHORT_LINK_EMPTY.getErrorCode());

        result = linksService.getOriginalLink("1234");
        Assert.assertEquals(result.getErrorCode(), ShortLinkErrorCode.SHORT_LINK_RULE_ERROR.getErrorCode());

        Mockito.when(shortLinkManager.getOriginalLink(Mockito.anyString())).thenThrow(new RuntimeException());
        result = linksService.getOriginalLink(SHORT_LINK);
        Assert.assertEquals(result.getErrorCode(), BaseErrorCode.SYSTEM_ERROR.getErrorCode());
    }
}