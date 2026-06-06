package com.heyenan.shorturldemo.service;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import com.heyenan.shorturldemo.datacache.ShortUrlFactory;
import com.heyenan.shorturldemo.strategy.ExecStrategy;
import com.heyenan.shorturldemo.strategy.HashStrategy;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author heyenan
 * @description 短域名业务层处理
 *
 * @date 2020/5/07
 */
@Service
public class ShortUrlService {

    @Resource
    private ShortUrlFactory urlDataCache;

    // 自定义长链接 - 防止重复字符串
    private static final String duplicate = "*";
    // bool过滤器
    private static final BitMapBloomFilter filter = BloomFilterUtil.createBitMap(10);

    /**
     * 获取短链接重定向地址
     *
     * @param shortUrl 短链接
     * @return 重定向地址
     */
    public String getOriginUrl(String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            return getLongUrlOr404(shortUrl);
        }
        // 先从缓存中获取
        String originUrl = urlDataCache.getOriginUrl(ShortUrlFactory.get().getShortUrlDataCache(), shortUrl);
        if (!StringUtils.isBlank(originUrl)) {
            return getLongUrlOr404(originUrl);
        }
        // 如果都没找到，则调转到404页面
        return getLongUrlOr404(originUrl);
    }


    /**
     * 获取404 页面或者 url页面
     *
     * @param url 长链接网址
     * @return 404 页面或者 url页面
     */
    private String getLongUrlOr404(String url) {
        return StringUtils.isBlank(url) ? "error/404/未找到" : url;
    }

    /**
     * 短域名Hash存储
     *
     * @param shortURL 短链接
     * @param longURL 长链接
     * @param originURL 原始链接
     * @return 长链接
     */
    @Generated
    public String saveUrlToCache(String shortURL, String longURL, String originURL) {
        // bool过滤器查找是否存在
        if (filter.contains(shortURL)) {
            // 没有缓存,长链接后加上指定字符串,重新hash
            longURL += duplicate;
            String shortUrl = new ExecStrategy(new HashStrategy()).getShortUrl(longURL);
            shortURL = saveUrlToCache(shortUrl, longURL, originURL);
        } else {
            // 不存在,存入缓存工厂
            Map<String, String> shortDataCache = ShortUrlFactory.get().getShortUrlDataCache();
            Map<String, String> longDataCache = ShortUrlFactory.get().getLongUrlDataCache();
            Set<String> shortSet = shortDataCache.keySet();
            // 已经存在此短链接，则可能是布隆过滤器误判，在长链接后加上指定字符串，重新hash
            if (shortSet.contains(shortURL)) {
                String shortUrl = new ExecStrategy(new HashStrategy()).getShortUrl(longURL);
                longURL += duplicate;
                shortURL = saveUrlToCache(shortUrl, longURL, originURL);
            }
            shortDataCache.put(shortURL, originURL);
            longDataCache.put(originURL, shortURL);
            filter.add(shortURL);
        }
        return shortURL;
    }

    /**
     * 短域名id增长存储
     *
     * @param shortURL 短链接
     * @param longURL 长链接
     * @return 长链接
     */
    public String saveUrlToCacheById(String shortURL, String longURL) {
        if (filter.contains(shortURL)) {
            // 没有缓存,返回空
            return null;
        } else {
            // 不存在,存入数据缓存
            Map<String, String> shortDataCache = ShortUrlFactory.get().getShortUrlDataCache();
            Map<String, String> longDataCache = ShortUrlFactory.get().getLongUrlDataCache();
            shortDataCache.put(shortURL, longURL);
            longDataCache.put(longURL, shortURL);
            filter.add(shortURL);
        }
        return shortURL;
    }


}
