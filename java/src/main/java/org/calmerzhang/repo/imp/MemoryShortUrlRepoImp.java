package org.calmerzhang.repo.imp;

import org.calmerzhang.repo.api.ShortUrlRepo;
import org.calmerzhang.repo.model.ShortUrlMappingPO;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 内存实现
 *
 * @author calmerZhang
 * @create 2022/01/10 10:40 上午
 */
@Component
public class MemoryShortUrlRepoImp implements ShortUrlRepo {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int MAX_REAL_SIZE = 2048;
    private static final int MAX_CAPLITY = (int) (MAX_REAL_SIZE / LOAD_FACTOR) - 1;

    private static final LinkedHashMap<String, ShortUrlMappingPO> short2LongUrlMap = new LRULinkedHashMap<>(MAX_CAPLITY, LOAD_FACTOR);
    private static final LinkedHashMap<String, ShortUrlMappingPO> long2ShortUrlMap = new LRULinkedHashMap<>(MAX_CAPLITY, LOAD_FACTOR);
    private static final String[] domains = {"http://www.baidu.com/"};

    @Override
    public synchronized int saveLongShortUrl(ShortUrlMappingPO mappingPO) {
        short2LongUrlMap.put(mappingPO.getShortUrl(), mappingPO);
        long2ShortUrlMap.put(mappingPO.getLongUrl(), mappingPO);

        return 1;
    }

    @Override
    public synchronized ShortUrlMappingPO getByLongUrl(String longUrl) {
        return long2ShortUrlMap.get(longUrl);
    }

    @Override
    public synchronized ShortUrlMappingPO getByShortUrl(String shortUrl) {
        return short2LongUrlMap.get(shortUrl);
    }

    @Override
    public String getDomain() {
        return domains[0];
    }

    static class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

        public LRULinkedHashMap(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return this.size() > MAX_CAPLITY;
        }
    }
}
