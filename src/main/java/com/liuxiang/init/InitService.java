package com.liuxiang.init;

import com.liuxiang.biz.ShortUrlBiz;
import com.liuxiang.dao.ShortUrlMappingDao;
import com.liuxiang.model.po.ShortUrlMappingPo;
import com.liuxiang.util.BloomSingleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 初始化将数据库数据加入到缓存和布隆过滤器
 *
 * @author liuxiang6
 * @date 2022-01-09
 **/
@Component
@Slf4j
public class InitService {

    @Resource
    private ShortUrlMappingDao shortUrlMappingDao;

    @PostConstruct
    public void init() {
        //初始化布隆过滤器
        BloomSingleton.getInstance();
        long startId = 0;
        List<ShortUrlMappingPo> shortUrlMappingPos = shortUrlMappingDao.getListFromId(startId);
        while (!CollectionUtils.isEmpty(shortUrlMappingPos)) {
            log.info("InitService.init id:{}", startId);
            startId = shortUrlMappingPos.get(shortUrlMappingPos.size() - 1).getId();

            shortUrlMappingPos.forEach(s -> {
                BloomSingleton.getInstance().put(s.getShortUrl());
                ShortUrlBiz.LOCAL_CACHE.put(s.getShortUrl(), s.getLongUrl());
            });

            shortUrlMappingPos = shortUrlMappingDao.getListFromId(startId);
        }
    }
}
