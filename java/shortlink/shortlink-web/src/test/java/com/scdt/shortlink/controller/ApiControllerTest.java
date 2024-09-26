package com.scdt.shortlink.controller;

import com.scdt.shortlink.client.domain.BaseErrorCode;
import com.scdt.shortlink.client.domain.ShortLinkErrorCode;
import com.scdt.shortlink.client.dto.Result;
import com.scdt.shortlink.dto.ResponseDTO;
import com.scdt.shortlink.service.impl.LinksServiceImpl;
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
 * @Author tzf
 * @Date 2022/4/28
 */
@RunWith(PowerMockRunner.class)
public class ApiControllerTest {

    @InjectMocks
    private ApiController apiController;

    @Mock
    private LinksServiceImpl linksService;

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
    public void testGetOriginalLink() {
        Mockito.when(linksService.getOriginalLink(Mockito.anyString())).thenReturn(Result.success(ORIGINAL_LINK));
        ResponseDTO responseDTO = apiController.getOriginalLink(SHORT_LINK);
        Assert.assertTrue(responseDTO.getResult());
        Assert.assertTrue(responseDTO.getIsSuccess());
        Assert.assertEquals(responseDTO.getModel(), ORIGINAL_LINK);

        Mockito.when(linksService.getOriginalLink(Mockito.anyString())).thenReturn(mockNotSuccessResult());
        responseDTO = apiController.getOriginalLink(SHORT_LINK);
        Assert.assertFalse(responseDTO.getResult());
        Assert.assertTrue(responseDTO.getIsSuccess());
        Assert.assertEquals(responseDTO.getErrorCode(), BaseErrorCode.SYSTEM_ERROR.getErrorCode());

        Mockito.when(linksService.getOriginalLink(Mockito.anyString())).thenReturn(
            Result.error(ShortLinkErrorCode.SHORT_LINK_RULE_ERROR));
        responseDTO = apiController.getOriginalLink(SHORT_LINK);
        Assert.assertEquals(responseDTO.getErrorCode(), ShortLinkErrorCode.SHORT_LINK_RULE_ERROR.getErrorCode());
    }

    @Test
    public void testSave() {
        Mockito.when(linksService.getShortLink(Mockito.anyString())).thenReturn(Result.success(SHORT_LINK));
        ResponseDTO responseDTO = apiController.save(ORIGINAL_LINK);
        Assert.assertTrue(responseDTO.getResult());
        Assert.assertTrue(responseDTO.getIsSuccess());
        Assert.assertEquals(responseDTO.getModel(), SHORT_LINK);

        Mockito.when(linksService.getShortLink(Mockito.anyString())).thenReturn(mockNotSuccessResult());
        responseDTO = apiController.save(ORIGINAL_LINK);
        Assert.assertFalse(responseDTO.getResult());
        Assert.assertTrue(responseDTO.getIsSuccess());
        Assert.assertEquals(responseDTO.getErrorCode(), BaseErrorCode.SYSTEM_ERROR.getErrorCode());

        Mockito.when(linksService.getShortLink(Mockito.anyString())).thenReturn(
            Result.error(ShortLinkErrorCode.SHORT_LINK_RULE_ERROR));
        responseDTO = apiController.save(ORIGINAL_LINK);
        Assert.assertEquals(responseDTO.getErrorCode(), ShortLinkErrorCode.SHORT_LINK_RULE_ERROR.getErrorCode());
    }

    /**
     * mock调用失败的返回值
     *
     * @return
     */
    private Result mockNotSuccessResult() {
        Result result = new Result();
        result.setResult(true);
        result.setIsSuccess(false);
        result.setErrorCode(BaseErrorCode.SYSTEM_ERROR.getErrorCode());
        result.setErrorMessage(BaseErrorCode.SYSTEM_ERROR.getErrorMessgae());
        return result;
    }
}