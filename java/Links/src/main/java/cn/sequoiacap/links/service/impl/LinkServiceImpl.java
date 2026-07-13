package cn.sequoiacap.links.service.impl;

import cn.sequoiacap.links.base.config.LinkConfig;
import cn.sequoiacap.links.dao.LinkDao;
import cn.sequoiacap.links.entities.Link;
import cn.sequoiacap.links.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Liushide
 * @date :2022/4/6 10:48
 * @description : 链接服务实现类
 */
@Slf4j
@Service
public class LinkServiceImpl implements LinkService {

    /**
     * link Dao
     */
    @Autowired
    private LinkDao linkDaoCache;

    @Override
    public void addLink(Link link) throws RuntimeException {
        String shortCode = link.getShortCode() ;
        String longLink = link.getLongLink();
        // 设置 shortLink
        link.setShortLink( LinkConfig.SHORT_CODE_DNS + shortCode );

        log.info( "add link shortCode={} ,longLink={}, shortLink={} " , shortCode, longLink, link.getShortLink() );

        // 保存到 cache
        linkDaoCache.addLink(link);
        // 其他操作 - 添加到数据库等，这里暂时不需要
    }

    @Override
    public Link getLinkByCode(String shortCode) throws RuntimeException {
        //从 cache 取值获取 link，如果有数据库操作，需要考虑 缓存击穿，缓存穿透，缓存雪崩 的情况，这里暂时不用考虑
        // 其他操作，这里暂时不需要
        return linkDaoCache.getLinkByCode(shortCode);
    }

}
