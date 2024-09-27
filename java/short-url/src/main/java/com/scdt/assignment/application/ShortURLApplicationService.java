package com.scdt.assignment.application;

import com.scdt.assignment.infrastructure.utils.IDUtil;
import com.scdt.assignment.infrastructure.utils.NumericConvertUtil;
import com.scdt.cache.Cache;
import com.scdt.cache.lru.LRUCache;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 应用层服务
 */
@Service
public class ShortURLApplicationService {

    /**
     * 短链接与长连接的映射关系，默认采用LRU算法进行内存淘汰
     */
    private Cache<Long, String> shortLongCache = new LRUCache();
    private Cache<String, Long> longShortCache = new LRUCache();

    /**
     * 根据url获取 短链接
     * @param url
     * @return
     */
    public String getShortUrl(String url) {
         Long id = longShortCache.get((String)url);
         if (ObjectUtils.isEmpty(id)) {
             id = IDUtil.nextId();
             longShortCache.put(url, id);
             shortLongCache.put(id, url);
         }
        return NumericConvertUtil.toOtherNumber(id);
    }


    /**
     * 根据短url获取原始url
     * @param url
     * @return
     */
    public String getOriginUrl(String url) {
        return shortLongCache.get(NumericConvertUtil.toDecimalNumber(url));

    }
}
