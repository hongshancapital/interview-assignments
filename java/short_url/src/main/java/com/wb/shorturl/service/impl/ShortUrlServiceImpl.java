package com.wb.shorturl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.shorturl.entity.ShortUrl;
import com.wb.shorturl.mapper.ShortUrlMapper;
import com.wb.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author: bing.wang
 * @date: 2021/1/8
 */

@Service
public class ShortUrlServiceImpl extends ServiceImpl<ShortUrlMapper, ShortUrl> implements ShortUrlService {


    @Autowired
    private ShortUrlMapper shortUrlMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${short-url.expireSecond}")
    private long expireSecond;

    /**
     * 保存转换关系
     *
     * @param entity the ShortUrl entity
     * @return the save result
     */
    @Override
    public boolean save(ShortUrl entity) {

        //将短码和长网址各做key存redis并设置过期时间
        redisTemplate.opsForValue().set(entity.getShortCode(), entity.getOriginUrl(), expireSecond, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(entity.getOriginUrl(), entity.getShortCode(), expireSecond, TimeUnit.SECONDS);

        //将转换关系存mysql
        return super.save(entity);
    }


    /**
     * 根据短码查询长网址
     *
     * @param code the shortCode
     * @return the originUrl
     */
    @Override
    public String getOriginUrlByShortCode(String code) {
        //从redis取短码对应长网址，如果有就直接返回
        boolean hasKey = redisTemplate.hasKey(code);
        if (hasKey) {
            return (String) redisTemplate.opsForValue().get(code);
        }
        //redis没有查到，从mysql取短码对应长网址，如果有就存redis一份并设置过期时间
        QueryWrapper<ShortUrl> wrapper = new QueryWrapper<>();
        wrapper.eq("short_code", code);
        ShortUrl shortUrl = shortUrlMapper.selectOne(wrapper);
        if (shortUrl != null) {
            redisTemplate.opsForValue().set(code, shortUrl.getOriginUrl(), expireSecond, TimeUnit.SECONDS);
        }
        return shortUrl == null ? null : shortUrl.getOriginUrl();
    }


    /**
     * 根据长网址查询短码
     *
     * @param url the originUrl
     * @return the shortCode
     */
    @Override
    public String getShortCodeByOriginUrl(String url) {

        //从redis长网址对应短码，如果有就直接返回
        boolean hasKey = redisTemplate.hasKey(url);
        if (hasKey) {
            return (String) redisTemplate.opsForValue().get(url);
        }

        //redis没有查到，从mysql长网址对应短码，如果有就存redis一份并设置过期时间
        QueryWrapper<ShortUrl> wrapper = new QueryWrapper<>();
        wrapper.eq("origin_url_hash", url.hashCode());
        List<ShortUrl> list = shortUrlMapper.selectList(wrapper);
        if(list.size()==0){
            return null;
        }
        for(ShortUrl item:list){
            if(item.getOriginUrl().equals(url)){
                 redisTemplate.opsForValue().set(url, item.getShortCode(), expireSecond, TimeUnit.SECONDS);
                 return item.getShortCode();
            }
        }
        return null;
    }

}
