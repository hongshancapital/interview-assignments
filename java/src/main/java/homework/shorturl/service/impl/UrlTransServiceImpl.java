package homework.shorturl.service.impl;

import homework.shorturl.exception.UrlTransException;
import homework.shorturl.model.dto.UrlTransDTO;
import homework.shorturl.service.UrlTransService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UrlTransServiceImpl implements UrlTransService {
    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    @Value("${map.initial.capacity}")
    private int initialCapacity;
    @Value("${retry.count}")
    private int retryCount;
    @Value("${map.factor}")
    private double mapFactor;
    private static Map<String, String> linkedMap;
    private static Map<String, String> longToShortMap;
    private static Map<String, String> shortToLongMap;
    private static ReentrantLock lock = new ReentrantLock();
    private static ReentrantLock lruLock = new ReentrantLock();

    @PostConstruct
    public void init() {
        linkedMap = new LinkedHashMap<>(initialCapacity);
        longToShortMap = new ConcurrentHashMap<>(initialCapacity);
        shortToLongMap = new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * 根据长链获取短链
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public UrlTransDTO transShort(UrlTransDTO dto) throws Exception {
        //若已有该长链的映射，直接返回短链
        if (longToShortMap.containsKey(dto.getUrl())) {
            String shortUrl = longToShortMap.get(dto.getUrl());
            dto.setShortUrl(shortUrl);
            lruRefresh(shortUrl);
            return dto;
        }
        //为避免短链冲突，设置最多尝试次数，失败则报错范围
        for (int i = 0; i < retryCount; i++) {
            String shortUrl = getRandomShortUrl();
            try {
                insert(shortUrl, dto.getUrl());
                dto.setShortUrl(shortUrl);
                return dto;
            } catch (Exception e) {
                continue;
            }
        }
        throw new UrlTransException("短链冲突，插入失败");
    }

    /**
     * 根据短链获取长链
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public UrlTransDTO transLong(UrlTransDTO dto) throws Exception {
        String url = shortToLongMap.get(dto.getShortUrl());
        if (!StringUtils.hasText(url)) {
            throw new UrlTransException("该短链不存在或已过期");
        }
        dto.setUrl(url);
        lruRefresh(dto.getShortUrl());
        return dto;
    }

    /**
     * 随机生成8位0-61的数字，通过字符下标拼接成8位的短链
     *
     * @return
     */
    public String getRandomShortUrl() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(62);
            builder.append(encodes[index]);
        }
        return builder.toString();
    }

    /**
     * 同步插入数据，避免同时插入相同的短链造成映射覆盖丢失
     *
     * @param shortUrl
     * @param longUrl
     * @return
     * @throws Exception
     */
    private void insert(String shortUrl, String longUrl) throws Exception {
        lock.lock();
        try {
            if (!shortToLongMap.containsKey(shortUrl)) {
                shortToLongMap.put(shortUrl, longUrl);
                longToShortMap.put(longUrl, shortUrl);
                lruAdd(shortUrl);
            } else {
                throw new UrlTransException("短链冲突，插入失败");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 因为是ConcurrentHashMap，移除不需要加锁操作
     *
     * @param shortUrl
     */
    private void remove(String shortUrl) {
        longToShortMap.remove(shortToLongMap.get(shortUrl));
        shortToLongMap.remove(shortUrl);
    }

    /**
     * 添加短链到lru缓存
     *
     * @param shortUrl
     */
    private void lruAdd(String shortUrl) {
        lruLock.lock();
        try {
            linkedMap.put(shortUrl, null);
            while (linkedMap.size() > initialCapacity * mapFactor) {
                String key = linkedMap.entrySet().iterator().next().getKey();
                remove(key);
                linkedMap.remove(key);
            }
        } finally {
            lruLock.unlock();
        }
    }

    /**
     * 查询后刷新lru缓存
     *
     * @param shortUrl
     */
    private void lruRefresh(String shortUrl) {
        lruLock.lock();
        try {
            linkedMap.remove(shortUrl);
            linkedMap.put(shortUrl, null);
        } finally {
            lruLock.unlock();
        }
    }
}
