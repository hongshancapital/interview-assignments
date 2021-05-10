package com.zs.shorturl.service.impl;

import com.zs.shorturl.cache.LongOrShortUrlCache;
import com.zs.shorturl.enity.vo.Result;
import com.zs.shorturl.service.IShortUrlService;
import com.zs.shorturl.utils.CommonUtil;
import com.zs.shorturl.utils.IdGenerateUtil;
import com.zs.shorturl.utils.ShortUrlGenerateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author zs
 * @date 2021/5/10
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {


    @Override
    public Result getShortUrlFromLongUrl(String longUrl) {
        //验证url是否合法
        boolean validUrl = CommonUtil.isValidUrl(longUrl);
        if (!validUrl){
            return Result.fail("地址不合法");
        }
        //查验是否是已经转换过的
        String shortUrl = LongOrShortUrlCache.getShortUrl(longUrl);
        if (null != shortUrl){
            return Result.success(shortUrl);
        }

        //生成短链接
        String serialCode = ShortUrlGenerateUtil.getSerialCode(IdGenerateUtil.generate());
        String completeShortUrl = ShortUrlGenerateUtil.getCompleteShortUrl(serialCode);

        //存放映射关系
        LongOrShortUrlCache.putLongForKey(longUrl,completeShortUrl);
        LongOrShortUrlCache.putShortForKey(completeShortUrl,longUrl);
        return Result.success(completeShortUrl);
    }

    @Override
    public Result getLongUrlFromShortUrl(String shortUrl) {
        if (!StringUtils.hasLength(shortUrl) || !shortUrl.startsWith(ShortUrlGenerateUtil.SHORT_URL_PRE)){
            return Result.fail("短链接不合法");
        }
        String longUrl = LongOrShortUrlCache.getLongUrl(shortUrl);
        if (null != longUrl){
            return Result.success(longUrl);
        }

        return Result.fail();
    }
}
