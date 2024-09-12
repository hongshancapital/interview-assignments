package com.redwood.shorturl.service;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;
import com.redwood.shorturl.common.BizException;
import com.redwood.shorturl.service.inter.ShortUrlService;
import com.redwood.shorturl.util.NumericConvertUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链服务：
 * 1. 提供长链和短链的转换功能。
 * 2. 提供长链和短链的映射和存储。
 *
 * @author Jack-ZG
 * @Date 2022-01-02
 */
@Service
public class ShortUrlServiceImpl implements InitializingBean, ShortUrlService {

    private static final ConcurrentLinkedHashMap<String, String> SHORT_URL_MAP = new ConcurrentLinkedHashMap.Builder<String, String>().maximumWeightedCapacity(16)
            .weigher(Weighers.singleton()).build();
    @Value("${shortUrl.storage.size}")
    private long capacity;
    //此值只会被赋值一次，后续会进行修改
    private int siteSize;

    @Value("#{'${shortUrl.serviceSites}'.split(',')}")
    private List<String> serviceSites;

    @Value("${shortUrl.rehash.size}")
    private int reHashSize;

    @Override
    public void afterPropertiesSet() throws Exception {
        SHORT_URL_MAP.setCapacity(capacity);
        siteSize = serviceSites.size();
        for (int i = 0; i < siteSize; i++) {
            serviceSites.set(i, serviceSites.get(i).trim());
        }
    }

    @Override
    public String exchange(String shortUrl) {
        String mainUrl = SHORT_URL_MAP.get(shortUrl);
        if (mainUrl == null) {
            throw new BizException("Have no mainUrl.");
        }
        return mainUrl;
    }

    @Override
    public String generate(String mainUrl) {
        long urlHashCode = (long) (mainUrl.hashCode()) + (long) Integer.MAX_VALUE + 1;
        int i = (int) (urlHashCode % (siteSize * reHashSize) / reHashSize);//二次hash计算获取主站序号。
        String host = serviceSites.get(i);
        String shortNumber = NumericConvertUtils.toOtherNumberSystem(urlHashCode, 62);
        String shortUrl = host + "/" + shortNumber;
        SHORT_URL_MAP.put(shortUrl, mainUrl);
        return shortUrl;
    }
}
