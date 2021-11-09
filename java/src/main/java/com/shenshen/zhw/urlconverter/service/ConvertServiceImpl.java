package com.shenshen.zhw.urlconverter.service;

import com.shenshen.zhw.urlconverter.utils.LocalCache;
import com.shenshen.zhw.urlconverter.utils.NumericConvertUtils;
import com.shenshen.zhw.urlconverter.utils.SnowFlake;
import org.springframework.stereotype.Service;

@Service
public class ConvertServiceImpl implements ConvertService {
    private final LocalCache LOCALCACHE = new LocalCache(10000);
    private final long EXPIRE = 60 * 1000; //过期时间

    @Override
    public String save(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            return null;
        }

        SnowFlake snowFlake = new SnowFlake(0, 0);
        long snowId = snowFlake.nextId();
        //数字和字母的8位组合最多只满足47位，高位1丢掉
        snowId = snowId & 0x7FFFFFFFFFFFL;
        String shortId = NumericConvertUtils.toOtherNumberSystem(snowId, 62);
        LOCALCACHE.put(shortId, longUrl, EXPIRE);
        return shortId;
    }

    @Override
    public String get(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            return null;
        }
        return LOCALCACHE.get(shortUrl);
    }


}
