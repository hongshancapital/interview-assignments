package com.scdt.shortenurl.storage.impl.cache;

import com.scdt.shortenurl.common.exception.BizException;
import com.scdt.shortenurl.common.utils.MemoryUtils;
import com.scdt.shortenurl.storage.ShortenUrlStorage;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Description 短链转换服务Storage层-本地缓存实现
 * @Author chenlipeng
 * @Date 2022/3/7 2:16 下午
 */
@Service("shortenUrlCache")
public class ShortenUrlCache implements ShortenUrlStorage {

    private final HashMap<String, String> shorten2OriginalMap = new HashMap<>();
    private final HashMap<String, String> original2shortenMap = new HashMap<>();

    @Override
    public void save(String shortenUrl, String originalUrl) {
        Boolean enough = MemoryUtils.checkMemoryEnough();
        if (!enough) {
            throw new BizException("超出短链生成的最大数量!!!");
        }

        shorten2OriginalMap.put(shortenUrl, originalUrl);
        original2shortenMap.put(originalUrl, shortenUrl);
    }

    @Override
    public String get(String shortenUrl) {
        return shorten2OriginalMap.get(shortenUrl);
    }

}
