package com.hszb.shorturl.service;

import com.hszb.shorturl.BaseTestSupport;
import com.hszb.shorturl.manager.ShortUrlManager;
import com.hszb.shorturl.model.ShortUrlResult;
import com.hszb.shorturl.model.request.TransferShortUrlRequest;
import com.hszb.shorturl.model.response.BaseResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/23 7:12 下午
 * @Version: 1.0
 * @Description:
 */
public class ShortUrlServiceExceptionTest extends BaseTestSupport {

    @Autowired
    private ShortUrlTransferService shortUrlTransferService;

    @MockBean
    private ShortUrlManager shortUrlManager;

    @Test
    public void transferShortUrlException () {
        Exception exception = new RuntimeException("test transferShortUrl exception");
        when(shortUrlManager.transferShortUrl(any(String.class))).thenThrow(exception);
        TransferShortUrlRequest transferShortUrlRequest = new TransferShortUrlRequest();
        List<String> longUrls = Arrays.asList("http://www.baidu.com", "http://www.souhu.com");
        transferShortUrlRequest.setLongUrls(longUrls);
        BaseResponse<List<ShortUrlResult>> response = shortUrlTransferService.transferShortUrl(transferShortUrlRequest);
        assert null != response && response.getResultCode().equals(BaseResponse.ResponseResult.ERROR.resultCode);
    }

    @Test
    public void queryLongUrlException () {
        Exception exception = new RuntimeException("test queryLongUrl exception");
        when(shortUrlManager.queryLongUrl(any(String.class))).thenThrow(exception);
        TransferShortUrlRequest transferShortUrlRequest = new TransferShortUrlRequest();
        BaseResponse<String> response = shortUrlTransferService.queryLongUrl("TEST");
        assert null != response && response.getResultCode().equals(BaseResponse.ResponseResult.ERROR.resultCode);
    }


}
