package com.ttts.urlshortener.service.impl;

import cn.hutool.core.lang.Validator;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import com.ttts.urlshortener.base.util.NumberRadixUtils;
import com.ttts.urlshortener.domain.LUrlReq;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.domain.ShortUrlVO;
import com.ttts.urlshortener.service.LongValueCreateService;
import com.ttts.urlshortener.service.ShortUrlCreateService;
import com.ttts.urlshortener.service.ShortUrlService;
import com.ttts.urlshortener.service.UrlQueryService;
import java.nio.charset.Charset;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 短链生成
 */

@Slf4j
@Service
public class ShortUrlCreateServiceImpl implements ShortUrlCreateService {
    //布隆过滤器，用于优化判断长链是否已经生成短链
    private static BloomFilter<String> LURL_DONE_TAGS = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")),
        1000000, 0.000001);

    //短链接域名
    @Value(value = "${shorturl.domain}")
    private String shortUrlDomain;

    private LongValueCreateService longValueCreateService;

    private UrlQueryService urlQueryService;

    private ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlCreateServiceImpl(
        LongValueCreateService longValueCreateService,
        UrlQueryService urlQueryService,
        ShortUrlService shortUrlService) {
        this.longValueCreateService = longValueCreateService;
        this.urlQueryService = urlQueryService;
        this.shortUrlService = shortUrlService;
    }


    /**
     * 短链生成，步骤如下：
     * 1. 前置参数检查，入参合法性
     * 2. 调用内部生成方法生成短链
     * 3. 用短域名和短连组合返回
     *
     * @param req 请求参数
     * @return
     */
    @Override
    public ShortUrlVO create(LUrlReq req) throws BusinessException {
        validParams(req);
        //判断是否已经生成
        ShortUrl shortUrl = internalCreate(req.getLurl());

        String surlStr = NumberRadixUtils.decimalToSixtyTwo(shortUrl.getSurl());
        String newUrl = String.format("%s/%s", getShortUrlDomain(), surlStr);

        ShortUrlVO record = new ShortUrlVO();
        record.setNewUrl(newUrl);
        record.setOrgUrl(shortUrl.getLurl());
        record.setExpiresTime(shortUrl.getExpiresTime());

        tagDone(req.getLurl());

        return record;
    }

    /**
     * 校验是否是合法URL
     *
     * @param req
     * @throws BusinessException
     */
    protected void validParams(LUrlReq req) throws BusinessException {
        if (req == null) {
            throw BusinessException.of(BaseResultCodeEnums.PARAMS_INVALID);
        }
        boolean isUrl = Validator.isUrl(req.getLurl());
        if (!isUrl) {
            throw BusinessException.of(BaseResultCodeEnums.PARAMS_INVALID);
        }
    }

    /**
     * 步骤如下
     * 1. 判断是否已经生成，若已经生成，否则继续, 这里需要优化，查询具体值性能消耗大，
 *      可以用布隆过滤器，查询大概是否存在优化
     * 2. 生成长整形数字
     * 3. 持久化
     * 4. 将长整形转换为字符串返回
     *
     * @param src
     * @return
     * @throws BusinessException
     */
    protected ShortUrl internalCreate(String src) throws BusinessException {

        boolean idDoneFuzzy = isDoneFuzzy(src);
        if (idDoneFuzzy) {
            //不确定是否已经生成
            Optional<ShortUrl> shortUrl = urlQueryService.queryShortUrl(src);
            if (shortUrl.isPresent()) {
                return shortUrl.get();
            }
        }
        Long lValue = longValueCreateService.create(src);
        //持久化
        ShortUrl record = shortUrlService.add(lValue, src);
        return record;
    }

    protected void tagDone(String key) {
        LURL_DONE_TAGS.put(key);
    }

    protected boolean isDoneFuzzy(String key) {
        return LURL_DONE_TAGS.mightContain(key);
    }
    public String getShortUrlDomain() {
        return shortUrlDomain;
    }
}
