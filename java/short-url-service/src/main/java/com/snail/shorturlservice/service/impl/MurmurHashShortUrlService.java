package com.snail.shorturlservice.service.impl;

import com.snail.shorturlservice.common.utils.NumberTo62Util;
import com.snail.shorturlservice.common.utils.ShortUrlUtil;
import com.snail.shorturlservice.dao.mapper.ShortUrlMapper;
import com.snail.shorturlservice.exception.DataInsertException;
import com.snail.shorturlservice.po.ShortUrlPO;
import com.snail.shorturlservice.properties.ApplicationProperties;
import com.snail.shorturlservice.redis.RedisRepository;
import com.snail.shorturlservice.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MurmurHashShortUrlService implements ShortUrlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MurmurHashShortUrlService.class);

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private ShortUrlMapper shortUrlMapper;

    @Override
    public String shorten(String url) {
        String domain = this.applicationProperties.getDomain();
        long longHashCode = ShortUrlUtil.getLongHashCode(url);
        String key = NumberTo62Util.to62(longHashCode, 6);
        ShortUrlPO newPo = null;

        //用哈希值字段查数据库，可能出现哈希冲突的多条记录
        List<ShortUrlPO> poList = this.shortUrlMapper.findByLongHashCode(longHashCode);
        if (null != poList && !poList.isEmpty()) {
            //有哈希冲突的记录，在结果集里匹配与本次参数url相同的记录
            Optional<ShortUrlPO> poOptional = poList.stream().filter(shortUrlPO -> shortUrlPO.getLongUrl().equals(url)).findAny();
            if (poOptional.isPresent()){
                // 找到匹配记录，缓存并返回结果
                ShortUrlPO po = poOptional.get();
                key = po.getShortKey();
                this.redisRepository.setShortUrl(key, url);
                String shortUrl = domain + key;
                return shortUrl;
            }else {
                // 没有匹配记录，给新增数据编号，并插入新增数据
                int seq = poList.size();
                String seqKey = NumberTo62Util.to62(seq);
                seqKey = key + seqKey;
                newPo = new ShortUrlPO();
                newPo.setShortKey(seqKey);
                newPo.setLongUrl(url);
                newPo.setLongHashCode(longHashCode);
                newPo.setSeq(seq);
            }
        }

        if (null == newPo){
            //新建，插入数据库，缓存
            newPo = new ShortUrlPO();
            newPo.setShortKey(key);
            newPo.setLongUrl(url);
            newPo.setLongHashCode(longHashCode);
        }

        int count = 0;
        try {
            count = this.shortUrlMapper.insert(newPo);
        } catch (Exception e) {
            // 入库失败，输出日志，抛出异常
            LOGGER.error("数据入库异常", e);
            throw new DataInsertException("服务繁忙，请重试");
        }
        if (count == 1){
            // 入库成功，缓存并返回结果
            this.redisRepository.setShortUrl(newPo.getShortKey(), url);
            String shortUrl = domain + newPo.getShortKey();
            return shortUrl;
        }else {
            // 入库失败，抛出异常
            throw new DataInsertException("服务异常，请重试");
        }
    }

    @Override
    public String getLongUrl(String shortUrlKey) {
        String[] arr = shortUrlKey.split("/");
        shortUrlKey = arr[arr.length - 1];
        //先从缓存中获取
        String longUrl = this.redisRepository.getLongUrl(shortUrlKey);
        if (null != longUrl) {
            return longUrl;
        }

        //再从数据库获取
        ShortUrlPO po = this.shortUrlMapper.findByShortKey(shortUrlKey);
        if (null != po) {
            longUrl = po.getLongUrl();
        }
        if (null == longUrl) longUrl = "";
        //放入缓存,防止缓存穿透
        this.redisRepository.setShortUrl(shortUrlKey, longUrl);

        return longUrl;
    }
}
