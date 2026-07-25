package com.scdt.job.lsx.service.impl;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.hash.Hashing;
import com.scdt.job.lsx.common.constant.CommonConstant;
import com.scdt.job.lsx.service.IShortUrlService;
import com.scdt.job.lsx.util.Base62Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lsx
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    @Resource
    private Cache<String, String> cache;

    @Override
    public String nativeUrlToShort(String nativeUrl) {
        // 1. Murmur Hash将 url转为hash值
        long hashCode = Hashing.murmur3_32().hashUnencodedChars(nativeUrl).padToLong();

        // 2. 对hash值使用Base62编码
        String shortLink = Base62Encoder.toBase62(hashCode);

        // 3. 将短链接与长链接映射关系存到本地缓存
        if(null != cache.getIfPresent(shortLink)) {
            // 4. 如果存在hash冲突，在原始URL后面添加固定字符串，反查时去掉尾部重复字符串即可
            this.nativeUrlToShort(nativeUrl + CommonConstant.REPEAT_FLAG);
        }else{
            cache.put(shortLink, nativeUrl);
        }

        return CommonConstant.BASE_URL + shortLink;
    }

    @Override
    public String shortUrlToNative(String shortUrl) {
        if (!shortUrl.startsWith(CommonConstant.BASE_URL)) {
            return org.apache.logging.log4j.util.Strings.EMPTY;
        }
        String shortLink = shortUrl.substring(CommonConstant.BASE_URL.length());
        String nativeUrl = cache.getIfPresent(shortLink);
        while (!Strings.isNullOrEmpty(nativeUrl) && nativeUrl.endsWith(CommonConstant.REPEAT_FLAG)) {
            nativeUrl = nativeUrl.substring(0, nativeUrl.length() - CommonConstant.REPEAT_FLAG.length());
        }
        return nativeUrl;
    }
}
