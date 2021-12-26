package com.hszb.shorturl.service;

import com.hszb.shorturl.model.ShortUrlResult;
import com.hszb.shorturl.model.request.TransferShortUrlRequest;
import com.hszb.shorturl.model.response.BaseResponse;

import java.util.List;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 4:36 下午
 * @Version: 1.0
 * @Description:
 */


public interface ShortUrlTransferService {

    BaseResponse<List<ShortUrlResult>> transferShortUrl (TransferShortUrlRequest request);

    BaseResponse<String> queryLongUrl (String shortCode);
}
