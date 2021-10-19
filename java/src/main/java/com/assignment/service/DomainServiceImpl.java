package com.assignment.service;

import com.assignment.cache.UrlCache;
import com.assignment.common.Constants;
import com.assignment.common.enums.ErrorCode;
import com.assignment.common.utils.ShortUrlUtils;
import com.assignment.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 短域名服务service
 *
 * @author shifeng
 */
@Service
public class DomainServiceImpl implements DomainService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result getShortUrl(String longUrl) {
        logger.info("调用获取短链接接口服务[getShortUrl],请求参数：{}", longUrl);
        if (StringUtils.isBlank(longUrl)) {
            return Result.error(ErrorCode.REQ_PARAM_EMPTY.getCode(), "请求url不能为空");
        }
        String shortUrl = UrlCache.getUrl(Constants.LONG_SHORT_PREFIX + longUrl);
        if (StringUtils.isBlank(shortUrl)) {
            shortUrl = ShortUrlUtils.getShortUrl();
            UrlCache.put(Constants.LONG_SHORT_PREFIX + longUrl, shortUrl);
            UrlCache.put(Constants.SHORT_LONG_PREFIX + shortUrl, longUrl);
        }
        logger.info("调用获取短链接接口服务完成,longUrl：{},shortUrl：{}", longUrl, shortUrl);
        return Result.ok().setData(Constants.BASE_URL + "/" + shortUrl);
    }

    @Override
    public Result getLongUrl(String shortUrl) {
        logger.info("查询短链接对应长连接服务[getLongUrl],请求参数{}", shortUrl);
        if (StringUtils.isBlank(shortUrl)) {
            return Result.error(ErrorCode.REQ_PARAM_EMPTY.getCode(), "请求url不能为空");
        }
        String rShortUrl = shortUrl.replace(Constants.BASE_URL + "/", "");
        String longUrl = UrlCache.getUrl(Constants.SHORT_LONG_PREFIX + rShortUrl);
        if (StringUtils.isBlank(longUrl)) {
            logger.warn("根据shortUrl:{}未查询到对应longUrl", shortUrl);
            return Result.error(ErrorCode.SHORT_URL_INVALID.getCode(), "链接走丢了");
        }
        return Result.ok().setData(longUrl);
    }
}
