package com.hszb.shorturl.service;

import com.hszb.shorturl.BaseTestSupport;
import com.hszb.shorturl.common.enums.ResponseCodeMsg;
import com.hszb.shorturl.model.ShortUrlResult;
import com.hszb.shorturl.model.request.TransferShortUrlRequest;
import com.hszb.shorturl.model.response.BaseResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/20 2:18 下午
 * @Version: 1.0
 * @Description:
 */
public class ShortUrltServiceTest extends BaseTestSupport {

    @Autowired
    private ShortUrlTransferService shortUrlTransferService;

    List<ShortUrlResult> shortUrllist;

    @Before
    public void transferShortUrl () {
        TransferShortUrlRequest transferShortUrlRequest = new TransferShortUrlRequest();
        List<String> longUrls = Arrays.asList("http://www.baidu.com", "http://www.souhu.com");
        transferShortUrlRequest.setLongUrls(longUrls);
        BaseResponse<List<ShortUrlResult>> response = shortUrlTransferService.transferShortUrl(transferShortUrlRequest);
        shortUrllist = response.getContent();
        boolean result = null != response && response.checkSuccess() && response.getContent().size() == 2;
        assert result;
    }

    @Test
    public void transferShortUrlParam () {
        BaseResponse<List<ShortUrlResult>> response = shortUrlTransferService.transferShortUrl(null);
        assert null != response && response.getResultCode().equals(ResponseCodeMsg.PARAM_IS_NULL.code);

        TransferShortUrlRequest transferShortUrlRequest = new TransferShortUrlRequest();
        BaseResponse<List<ShortUrlResult>> response2 = shortUrlTransferService.transferShortUrl(transferShortUrlRequest);
        assert null != response2 && response2.getResultCode().equals(ResponseCodeMsg.PARAM_IS_NULL.code);
    }


    @Test
    public void queryLongUrl () {
        assert null != shortUrllist;
        for (ShortUrlResult result : shortUrllist) {
            BaseResponse<String> response = shortUrlTransferService.queryLongUrl(result.getShortCode());
            assert null != response && response.checkSuccess() && null != response.getContent();
        }
    }

    @Test
    public void queryLongUrlParam () {
        BaseResponse<String> nullParamResponse = shortUrlTransferService.queryLongUrl(null);
        assert null != nullParamResponse && nullParamResponse.getResultCode().equals(ResponseCodeMsg.PARAM_IS_NULL.code);

        BaseResponse<String> validParamResponse = shortUrlTransferService.queryLongUrl("TEST");
        assert null != validParamResponse && validParamResponse.getResultCode().equals(ResponseCodeMsg.INVALID_URL.code);
    }
}
