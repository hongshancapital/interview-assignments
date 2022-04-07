package cn.sequoiacap.links.dao.impl;

import cn.sequoiacap.links.dao.LinkDao;
import cn.sequoiacap.links.entities.Link;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Liushide
 * @date :2022/4/6 10:15
 * @description : 链接Dao缓存实现类
 */
@Slf4j
@Component
public class LinkDaoCacheImpl implements LinkDao {
    /**
     * caffeineCache 存储对象
     */
    @Autowired
    Cache<String, Object> caffeineCache;

    @Override
    public void addLink(Link link) {
        // 设置缓存
        caffeineCache.put(link.getShortCode(), link);
    }

    @Override
    public Link getLinkByCode(String shortCode) {
        // 先从缓存中判断是否有
        if( caffeineCache.getIfPresent(shortCode) == null) {
            log.info("从缓存没有查询到信息 shortCode= {}", shortCode);
            return null;
        }
        //如果有从缓存中获取
        Link link = (Link) caffeineCache.asMap().get(shortCode);
        log.info("从缓存中获取了信息 shortCode={} , longLink={}",link.getShortCode(), link.getLongLink());
        return link;
    }
}
