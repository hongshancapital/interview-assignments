package com.heyenan.shorturldemo.webservice.controller;

import com.heyenan.shorturldemo.common.request.ShortUrlRequest;
import com.heyenan.shorturldemo.common.response.Encode;
import com.heyenan.shorturldemo.common.response.ShortUrlResponse;
import com.heyenan.shorturldemo.datacache.ShortUrlFactory;
import com.heyenan.shorturldemo.service.ShortUrlService;
import com.heyenan.shorturldemo.strategy.ExecStrategy;
import com.heyenan.shorturldemo.strategy.HashStrategy;
import com.heyenan.shorturldemo.untils.UrlCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author heyenan
 * @description 短域名接口
 * @date 2020/5/07
 */
@Controller
@Slf4j
@RequestMapping(value = "/")
@Api(tags = "短域名存储接口")
public class ShortUrlController {
    @Resource
    private ShortUrlService shortUrlService;
    @Resource
    private ShortUrlFactory urlDataCache;

    /**
     * @param request 生成短链接请求对象
     * @return 返回对象包含原始长链接, 生成短链接以及短链接生成类型(系统生成还是自定义的短链接)
     */
    @ResponseBody
    @PostMapping("/genShortUrl")
    @ApiOperation(
            value = "生成短链接",
            httpMethod = "POST",
            notes = "生成短链接",
            response = ShortUrlResponse.class)

    public ShortUrlResponse genShortUrl(
            @Valid @ApiParam(value = "请求对象", required = true) @RequestBody ShortUrlRequest request
    ) {
        // 入参校验
        try {
            String longUrl = request.getOriginUrl();
            if (StringUtils.isBlank(longUrl)) {
                return ShortUrlResponse.error(Encode.PARAMETER_VALIDATION);
            }
            if (UrlCheck.checkURL(longUrl)) {
                if (!longUrl.startsWith("http")) {
                    longUrl = "http://" + longUrl;
                }
                String shortUrlCache = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), longUrl);
                if (!StringUtils.isBlank(shortUrlCache)) {
                    return ShortUrlResponse.success(shortUrlCache);
                }
                String shortUrl = shortUrlService.saveUrlToCache(new ExecStrategy(new HashStrategy()).getShortUrl(longUrl), longUrl, longUrl);
                return ShortUrlResponse.success(shortUrl);
            } else {
                return ShortUrlResponse.error(Encode.LONG_URL_ERROR);
            }
        }catch (Exception e){
            return ShortUrlResponse.error(Encode.LONG_URL_ERROR);
        }
    }


}
