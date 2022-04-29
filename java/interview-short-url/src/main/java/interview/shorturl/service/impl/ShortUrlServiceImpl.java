package interview.shorturl.service.impl;

import interview.shorturl.dao.ShortUrlDao;
import interview.shorturl.dao.po.ShortUrlInfo;
import interview.shorturl.exception.BusException;
import interview.shorturl.response.ResponseCodeEnum;
import interview.shorturl.service.ShortUrlService;
import interview.shorturl.util.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 短域名业务操作
 *
 * @author: ZOUFANQI
 **/
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlDao shortUrlDao;

    public ShortUrlServiceImpl(ShortUrlDao shortUrlDao) {
        this.shortUrlDao = shortUrlDao;
    }

    @Override
    public ShortUrlInfo convertUrl(String realUrl, Integer expireSecond) {
        if (StringUtils.isBlank(realUrl)) {
            throw new BusException(ResponseCodeEnum.PARAM_ERROR);
        }

        final ShortUrlInfo info = new ShortUrlInfo();
        // 获取全局唯一ID
        final Long nextGlobalId = this.shortUrlDao.getNextGlobalId();
        // ID转字符（短地址）
        final String shortUrl = ConvertUtil.encode10To62(nextGlobalId);

        info.setId(nextGlobalId);
        info.setShortUrl(shortUrl);
        info.setRealUrl(realUrl);

        // 有失效时间
        if (expireSecond != null && expireSecond > 0) {
            info.setExpireOn(DateUtils.addSeconds(new Date(), expireSecond));
        }
        // 持久化数据
        this.shortUrlDao.upsertOne(info);
        return this.shortUrlDao.getInfoByShortUrl(shortUrl);
    }

    @Override
    public ShortUrlInfo getInfoByShortUrl(String shortUrl) {
        final ShortUrlInfo info = this.shortUrlDao.getInfoByShortUrl(shortUrl);
        if (info == null) {
            throw new BusException(ResponseCodeEnum.NOT_FOUND);
        }
        if (info.getExpireOn() != null && info.getExpireOn().before(new Date())) {
            throw new BusException(ResponseCodeEnum.EXPIRED, "地址已经过期");
        }
        return info;
    }
}
