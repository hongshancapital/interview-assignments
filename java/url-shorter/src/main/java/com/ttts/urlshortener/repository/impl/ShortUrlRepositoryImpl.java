package com.ttts.urlshortener.repository.impl;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.repository.ShortUrlRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * jvm存储短链-长链映射数据
 *
 */
@Repository
public class ShortUrlRepositoryImpl implements ShortUrlRepository {

    protected static int MAX_CAPACITY = 400000000;

    //内存模拟持久化数据
    protected static Map<Long, ShortUrl> SHOT_URL_MAP = new ConcurrentHashMap<>();

    //surl - id索引
    protected static Map<Long, Long> SURL_ID_MAP_INDEX = new ConcurrentHashMap<>();

    @Override
    public int add(ShortUrl value) {
        checkCapacity();
        SHOT_URL_MAP.put(value.getId(), value);
        SURL_ID_MAP_INDEX.put(value.getSurl(),value.getId());

        return 1;
    }

    @Override
    public int deleteById(Long id) {
        ShortUrl value = SHOT_URL_MAP.remove(id);
        if (value == null) {
            return 0;
        }
        SHOT_URL_MAP.remove(value.getSurl());
        return 1;
    }

    @Override
    public ShortUrl getBySurl(Long surl) {
        Long id = SURL_ID_MAP_INDEX.get(surl);
        if (id != null) {
            return SHOT_URL_MAP.get(id);
        }
        return null;
    }

    /**
     * 生产缓解，这里会根据lurl的MD5值索引
     * @param lurl
     * @return
     */
    @Override
    public List<ShortUrl> getByLurl(String lurl) {
        List<ShortUrl> result = new ArrayList<>();
        Set<Entry<Long, ShortUrl>> entrySet = SHOT_URL_MAP.entrySet();
        for (Entry<Long, ShortUrl> entry : entrySet) {
            if (lurl.equals(entry.getValue().getLurl())) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    @Override
    public List<ShortUrl> listAllShortUrl() {
        Set<Entry<Long, ShortUrl>> entrySet = SHOT_URL_MAP.entrySet();
        List<ShortUrl> records = entrySet.stream()
            .map(it -> it.getValue()).collect(Collectors.toList());
        return records;
    }

    protected void checkCapacity() {
        if (SHOT_URL_MAP.size() > MAX_CAPACITY) {
            throw BusinessException.of(BaseResultCodeEnums.LACK_CAPACITY);
        }
    }
}
