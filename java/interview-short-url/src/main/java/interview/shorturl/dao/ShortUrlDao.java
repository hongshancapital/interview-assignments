package interview.shorturl.dao;

import interview.shorturl.dao.po.ShortUrlInfo;
import interview.shorturl.exception.BusException;

/**
 * @author: ZOUFANQI
 **/
public interface ShortUrlDao {
    /**
     * 更新或插入数据
     *
     * @param shortUrlInfo 数据
     * @return 成功与否
     * @throws BusException 数据校验异常
     */
    boolean upsertOne(ShortUrlInfo shortUrlInfo) throws BusException;

    /**
     * 获取单条数据
     *
     * @param shortUrl 短地址
     * @return 对应数据
     */
    ShortUrlInfo getInfoByShortUrl(String shortUrl);

    /**
     * 全局ID生成器
     *
     * @return nextId
     */
    Long getNextGlobalId();
}
