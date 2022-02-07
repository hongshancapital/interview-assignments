package com.xg.shorturl.controller;

import com.xg.shorturl.common.BaseErrorCode;
import com.xg.shorturl.common.BaseResponse;
import com.xg.shorturl.common.BizException;
import com.xg.shorturl.request.GetShortUrlRequest;
import com.xg.shorturl.request.QueryOriginalUrlRequest;
import com.xg.shorturl.service.ShortUrlService;
import com.xg.shorturl.utils.UrlValidator;
import com.xg.shorturl.vo.OriginalUrlInfoVO;
import com.xg.shorturl.vo.ShortUrlInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xionggen
 */
@Api(value = "短域名接口")
@RestController
@RequestMapping("/xg/shortUrl")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation(value = "获取短链接")
    @ResponseBody
    @RequestMapping(value = "getShortUrl",method = RequestMethod.POST)
    public BaseResponse<ShortUrlInfoVO> getShortUrl(@Valid @RequestBody GetShortUrlRequest request) {
        if (!UrlValidator.isValidUrl(request.getOriginalUrl())) {
            throw new BizException("url不合法", BaseErrorCode.ERROR_PARAMETER);
        }
        String shortUrl = shortUrlService.queryOrGenerateShortUrl(request.getOriginalUrl());
        ShortUrlInfoVO shortUrlInfoVO = new ShortUrlInfoVO();
        shortUrlInfoVO.setShortUrl(shortUrl);
        return BaseResponse.<ShortUrlInfoVO>newSuccessResponse().result(shortUrlInfoVO).build();
    }

    @ApiOperation(value = "查询长链接")
    @ResponseBody
    @RequestMapping(value = "queryOriginalUrl",method = RequestMethod.POST)
    public BaseResponse<OriginalUrlInfoVO> queryOriginalUrl(@Valid @RequestBody QueryOriginalUrlRequest request) {
        if (!UrlValidator.isValidUrl(request.getShortUrl())) {
            throw new BizException("url不合法", BaseErrorCode.ERROR_PARAMETER);
        }
        String queryOriginalUrl = shortUrlService.queryOriginalUrl(request.getShortUrl());
        OriginalUrlInfoVO originalUrlInfoVO = new OriginalUrlInfoVO();
        originalUrlInfoVO.setOriginalUrl(queryOriginalUrl);
        return BaseResponse.<OriginalUrlInfoVO>newSuccessResponse().result(originalUrlInfoVO).build();
    }

}
