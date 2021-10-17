package com.zhangzheng.homework.service;

import com.google.common.hash.BloomFilter;
import com.zhangzheng.homework.entity.UrlMap;
import com.zhangzheng.homework.repository.UrlMapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: 异步执行任务
 * @date 2021/10/9 下午2:46
 */
@EnableAsync
@Service
@Slf4j
public class AsyncService {

    @Autowired
    private UrlMapRepository urlMapRepository;

    @Async
    public void saveUrlMap(String longUrl,String shortUrl){
        log.info("保存短链接url与长链接url对应关系，shortUrl={},longUrl={}",shortUrl,longUrl);
        if(!StringUtils.isEmpty(longUrl) && !StringUtils.isEmpty(shortUrl)){
            UrlMap record = new UrlMap();
            record.setLongUrl(longUrl);
            record.setShortUrl(shortUrl);
            record.setCreateTime(new Date());
            record.setModifyTime(new Date());
            urlMapRepository.save(record);
        }
    }
}
