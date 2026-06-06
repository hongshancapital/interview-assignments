package com.heyenan.shorturldemo.strategy;

import com.heyenan.shorturldemo.datacache.ShortUrlFactory;
import com.heyenan.shorturldemo.untils.NumberTransform;


/**
 * @author heyenan
 * @description id递增策略实现类
 *
 * @date 2020/5/07
 */
public class IdGrowStrategy implements ShortUrlStrategy {

    /** 定义Id递增基数 */
    private static final int baseNum = 1000000000;

    /**
     * 获取短连接
     *
     * @param longUrl 长链接
     * @return 短链接
     */
    @Override
    public String getShortUrl(String longUrl) {
        long id = ShortUrlFactory.get().getShortUrlDataCache().keySet().size();
        long growId = id + baseNum;
        String shortUrl = NumberTransform.convert10to62(growId, 6);
        return shortUrl;
    }
}
