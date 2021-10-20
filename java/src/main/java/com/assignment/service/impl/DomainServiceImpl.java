package com.assignment.service.impl;

import com.assignment.cache.UrlCache;
import com.assignment.common.Constants;
import com.assignment.common.enums.ErrorCode;
import com.assignment.common.utils.ShortUrlUtils;
import com.assignment.service.DomainService;
import com.assignment.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 短域名服务serviceImpl
 *
 * @author shifeng
 */
@Service
public class DomainServiceImpl implements DomainService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result getShortUrl(String longUrl) {
        logger.info("[调用获取短链接接口服务getShortUrl]请求参数：{}", longUrl);
        if (StringUtils.isBlank(longUrl)) {
            return Result.error(ErrorCode.REQ_PARAM_EMPTY.getCode(), "请求url不能为空");
        }
        String shortUrl = UrlCache.get(Constants.LONG_SHORT_PREFIX + longUrl);
        if (StringUtils.isBlank(shortUrl)) {
            shortUrl = ShortUrlUtils.getShortUrl();
            if (StringUtils.isNotBlank(shortUrl) && shortUrl.length() <= 8) {
                UrlCache.put(Constants.LONG_SHORT_PREFIX + longUrl, shortUrl);
                UrlCache.put(Constants.SHORT_LONG_PREFIX + shortUrl, longUrl);
            } else {
                logger.warn("[生成短链接异常] longUrl:{},shortUrl:{}", longUrl, shortUrl);
                return Result.error(ErrorCode.CREATE_SHORT_URL_ERROR.getCode(), "服务开小差了,暂无法生成短链接");
            }
        }
        shortUrl = Constants.BASE_URL + "/" + shortUrl;
        logger.info("[调用获取短链接接口服务结束]longUrl：{},shortUrl：{}", longUrl, shortUrl);
        return Result.ok().setData(shortUrl);
    }

    @Override
    public Result getLongUrl(String shortUrl) {
        logger.info("查询短链接对应长连接服务[getLongUrl],请求参数{}", shortUrl);
        if (StringUtils.isBlank(shortUrl)) {
            return Result.error(ErrorCode.REQ_PARAM_EMPTY.getCode(), "请求url不能为空");
        }
        String rShortUrl = shortUrl.replace(Constants.BASE_URL + "/", "");
        String longUrl = UrlCache.get(Constants.SHORT_LONG_PREFIX + rShortUrl);
        if (StringUtils.isBlank(longUrl)) {
            logger.warn("根据shortUrl:{}未查询到对应longUrl", shortUrl);
            return Result.error(ErrorCode.SHORT_URL_INVALID.getCode(), "链接走丢了");
        }
        return Result.ok().setData(longUrl);
    }
}
