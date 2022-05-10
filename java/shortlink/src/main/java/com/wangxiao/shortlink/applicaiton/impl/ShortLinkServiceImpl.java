package com.wangxiao.shortlink.applicaiton.impl;

import com.wangxiao.shortlink.applicaiton.ShortLinkService;
import com.wangxiao.shortlink.domain.shortlink.LinkPair;
import com.wangxiao.shortlink.domain.shortlink.LinkPairRepository;
import com.wangxiao.shortlink.infrastructure.common.PersistenceException;
import com.wangxiao.shortlink.infrastructure.common.StoreOverFlowException;
import com.wangxiao.shortlink.infrastructure.persisitence.PersistenceService;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import com.wangxiao.shortlink.infrastructure.register.RegisterCenter;
import com.wangxiao.shortlink.infrastructure.utils.MachineIdUtils;
import com.wangxiao.shortlink.infrastructure.utils.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class ShortLinkServiceImpl implements ShortLinkService {
    @Resource
    private LinkPairRepository linkPairRepository;
    @Resource
    private ShortLinkProperties shortLinkProperties;
    @Resource
    private RegisterCenter registerCenter;
    @Resource
    private PersistenceService persistenceService;

    @Override
    public String encodeUrl(String longLink) {
        //存储超过上限，则下线编码服务并抛出异常
        if (shortLinkProperties.getStoreLimit().compareTo(linkPairRepository.totalPairSize()) <= 0) {
            registerCenter.unRegisteMethod(shortLinkProperties.getMachineId(), "encodeUrl");
            log.error("存储到达上限！");
            throw new StoreOverFlowException();
        }
        Integer salt = 0;
        LinkPair linkPair = getLinkPair(longLink, salt);
        String saveResult = linkPairRepository.saveIfAbsent(linkPair);
        //当出现hash碰撞时
        //比较已存储的长链接及待存储的长链接若相同则直接返回短链
        //否则修改盐值继续hash直至不出现hash碰撞
        while (saveResult != null && !Objects.equals(longLink, saveResult)) {
            salt++;
            linkPair = getLinkPair(longLink, salt);
            saveResult = linkPairRepository.saveIfAbsent(linkPair);
        }
        //持久化数据
        if (saveResult == null) {
            try {
                persistenceService.persist(linkPair.getShortLink(), linkPair.getLongLink());
            } catch (Exception e) {
                linkPairRepository.removeLink(linkPair.getShortLink());
                log.error("持久化异常！", e);
                throw new PersistenceException();
            }
        }
        return linkPair.getShortLink();
    }

    @Override
    public String decodeUrl(String shortLink) {
        return linkPairRepository.getLongLink(shortLink);
    }


    private LinkPair getLinkPair(String longLink, Integer salt) {
        Long haseText = MappingUtils.hashing(longLink + salt);
        String base62Text = MappingUtils.encodeBase62(haseText);
        String shortLink = MachineIdUtils.combine(shortLinkProperties.getMachineId(), base62Text);
        LinkPair linkPair = new LinkPair(shortLink, longLink);
        return linkPair;
    }
}
