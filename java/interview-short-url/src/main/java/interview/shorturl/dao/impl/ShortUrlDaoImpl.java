package interview.shorturl.dao.impl;

import interview.shorturl.dao.ShortUrlDao;
import interview.shorturl.dao.po.ShortUrlInfo;
import interview.shorturl.exception.BusException;
import interview.shorturl.response.ResponseCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 模拟持久化数据层操作
 *
 * @author: ZOUFANQI
 **/
@Repository
public class ShortUrlDaoImpl implements ShortUrlDao {
    /**
     * 全局ID
     */
    private final static AtomicLong GLOBAL_ID = new AtomicLong(1024000000L);
    /**
     * 数据存储
     */
    private final static Map<String, ShortUrlInfo> SHORT_URL_TO_INFO_MAP = new ConcurrentHashMap<>(1024);

    @Override
    public boolean upsertOne(ShortUrlInfo shortUrl) {
        if (shortUrl.getId() == null ||
                StringUtils.isAnyBlank(shortUrl.getShortUrl(), shortUrl.getRealUrl())) {
            throw new BusException(ResponseCodeEnum.PARAM_ERROR);
        }
        shortUrl.setCreateOn(new Date());

        // 数据已存在，更新
        if (SHORT_URL_TO_INFO_MAP.containsKey(shortUrl.getShortUrl())) {
            return this.updateOne(shortUrl);
        }
        // 数据不存在，插入
        else {
            return this.insertOne(shortUrl);
        }
    }

    @Override
    public ShortUrlInfo getInfoByShortUrl(String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            return null;
        }
        return SHORT_URL_TO_INFO_MAP.get(shortUrl);
    }

    @Override
    public Long getNextGlobalId() {
        return GLOBAL_ID.getAndIncrement();
    }

    /**
     * 模拟更新数据
     *
     * @param shortUrl 数据
     * @return 成功与否
     */
    private boolean updateOne(ShortUrlInfo shortUrl) {
        SHORT_URL_TO_INFO_MAP.put(shortUrl.getShortUrl(), shortUrl);
        return true;
    }

    /**
     * 模拟插入数据
     *
     * @param shortUrlInfo 数据
     * @return 成功与否
     */
    private boolean insertOne(ShortUrlInfo shortUrlInfo) {
        SHORT_URL_TO_INFO_MAP.put(shortUrlInfo.getShortUrl(), shortUrlInfo);
        return true;
    }

}
