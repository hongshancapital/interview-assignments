package com.sequoia.web.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.sequoia.web.mapper.UrlMapper;
import com.sequoia.web.service.DWZService;
import com.sequoia.web.util.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DWZServiceImpl implements DWZService {
    // 随机数，设置一个幸运数字作为哈希盐
    private static final int SEED = 101784;
    private static final String DUPLICATE = "*";
    private HashFunction hashFunction = Hashing.murmur3_32_fixed(SEED);
    @Autowired
    private BloomFilter<String> FILTER;
    @Autowired
    private UrlMapper urlMapper;

    public String getLongUrlByShortUrl(String shortURL) {
        String longURL = null;
        if (FILTER.mightContain(shortURL)) {
            longURL = urlMapper.get(shortURL);
        }
        return longURL;
    }

    public String saveShortUrlByLongUrl(String originalURL) {
        String shortURL = generateShortUrl(originalURL);
        return saveUrlMap(shortURL, originalURL, originalURL);
    }

    protected String saveUrlMap(String shortURL, String hashURL, String originalURL) {
        // 在布隆过滤器中查找是否存在
        if (FILTER.mightContain(shortURL)) {
            // 如果库中已有,且等于originalURL直接返回
            if(originalURL.equals(urlMapper.get(shortURL))) {
                return shortURL;
            }
        }

        // 没有shortUrl对应的值时，已同步的方法写入map
        if(urlMapper.get(shortURL) == null) {
            synchronized (shortURL.intern()) {
                if (urlMapper.get(shortURL) == null) {
                    urlMapper.put(shortURL, originalURL);
                    FILTER.put(shortURL);
                    return shortURL;
                }
            }
        }

        // 如果shortUrl已被占用，但是值不同，说明hash冲突，加上padding字符串后重新hash
        hashURL += DUPLICATE;
        shortURL = generateShortUrl(hashURL);
        return saveUrlMap(shortURL, hashURL, originalURL);
    }

    // 生成47位(62进制最多8位)hash, 1-32位 murmur3_32hash值, 33-43位 hashCode取模, 后4位保留位
    protected String generateShortUrl(String hashURL){
        long hash1 = hashFunction.hashString(hashURL, Charsets.US_ASCII).padToLong();
        long hashCodeLong = Long.parseUnsignedLong(Integer.toUnsignedString(hashURL.hashCode()));
        long hash2 = hashCodeLong % 2048;
        long combinedHash = hash1<<15 | hash2<<4;
        String shortURL = Base62.encode(combinedHash);
        return shortURL;
    }

    public void setUrlMapper(UrlMapper urlMapper){
        this.urlMapper = urlMapper;
    }

}
