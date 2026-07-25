package com.hszb.shorturl.service.impl;

import com.hszb.shorturl.common.enums.ResponseCodeMsg;
import com.hszb.shorturl.manager.ShortUrlManager;
import com.hszb.shorturl.model.ShortUrlResult;
import com.hszb.shorturl.model.request.TransferShortUrlRequest;
import com.hszb.shorturl.model.response.BaseResponse;
import com.hszb.shorturl.service.ShortUrlTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/19 1:42 下午
 * @Version: 1.0
 * @Description: 短域名服务
 */

@Service
@Slf4j
public class ShortUrlTransferServiceImpl implements ShortUrlTransferService {

    @Autowired
    private ShortUrlManager shortUrlManager;

    @Override
    public BaseResponse<List<ShortUrlResult>> transferShortUrl(TransferShortUrlRequest request) {
        try {
            if (request == null || CollectionUtils.isEmpty(request.getLongUrls())) {
                return BaseResponse.fail(ResponseCodeMsg.PARAM_IS_NULL);
            }
            List<ShortUrlResult> shortUrlList = request.getLongUrls().stream().map(shortUrlManager::transferShortUrl).
                    collect(Collectors.toList());
            return BaseResponse.succeed(shortUrlList);
        } catch (Exception e) {
            log.error("shortUrlTransferService transferShortUrl error", e);
            return BaseResponse.error();
        }
    }

    @Override
    public BaseResponse<String> queryLongUrl(String shortCode) {
        try {
            if (shortCode == null) {
                return BaseResponse.fail(ResponseCodeMsg.PARAM_IS_NULL);
            }
            String longUrl = shortUrlManager.queryLongUrl(shortCode);
            if (null == longUrl) {
                return BaseResponse.fail(ResponseCodeMsg.INVALID_URL);
            }
            return BaseResponse.succeed(longUrl);
        } catch (Exception e) {
            log.error("{} shortUrlTransferService queryLongUrl error", shortCode, e);
            return BaseResponse.error();
        }
    }
}
