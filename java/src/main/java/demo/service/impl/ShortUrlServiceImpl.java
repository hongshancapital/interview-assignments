package demo.service.impl;

import demo.common.ShortUrlUtil;
import demo.service.RedisService;
import demo.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ShortUrlServiceImpl
 * @Description: 短长链接转换服务类实现
 * @author Xia
 * @version V1.0
 * @Date 2021/12/15
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private RedisService redisService;

    @Value("${redis.shortkey}")
    private String shortKey;

    @Override
    public String getLongUrl(String shortUrl) {
        String s = redisService.get(shortUrl);
        return s;
    }

    @Override
    public String getShortUrl(String longUrl) {
        ShortUrlUtil shortUrlUtil = ShortUrlUtil.getInstance();
        //1.生成短链接
        String shortUrl = shortUrlUtil.shortUrl(longUrl);
        //2.放入缓存
        redisService.put(shortUrl, longUrl);
        return shortUrl;
    }
}
