package com.scdt.shortlink.manager;

import com.scdt.shortlink.contants.ShortLinkContants;
import com.scdt.shortlink.core.cache.ICache;
import com.scdt.shortlink.core.engine.ShortLinkEngine;
import com.scdt.shortlink.exception.CreateShortLinkException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 短链服务manager层
 *
 * @Author tzf
 * @Date 2022/4/30
 */
@Component
public class ShortLinkManager {
    /**
     * 缓存接口
     */
    @Resource
    private ICache cache;

    private static final int CREATE_MAX_COUNT = 3;

    /**
     * 生成短链
     *
     * @param link 长链
     * @return
     */
    public String createShortLink(String link) {
        String shortLink = ShortLinkEngine.createShortLink(ShortLinkContants.SHORT_LINK_COUNT);

        //查重
        int i = 0;
        while (i < CREATE_MAX_COUNT && StringUtils.isNotBlank(getOriginalLink(shortLink))) {
            shortLink = ShortLinkEngine.createShortLink(ShortLinkContants.SHORT_LINK_COUNT);
            i++;
        }

        if (StringUtils.isNotBlank(getOriginalLink(shortLink))) {
            //最终生成的短链仍然已存在，则放弃生成，返回异常
            throw new CreateShortLinkException("生成短链重复");
        }

        if (StringUtils.isEmpty(shortLink)) {
            throw new CreateShortLinkException("短链生成为空");
        }

        //缓存长短链映射关系
        cache.put(shortLink, link);

        return shortLink;
    }

    /**
     * 通过短链获取长链
     *
     * @param shortLink 短链
     * @return
     */
    public String getOriginalLink(String shortLink) {
        return cache.get(shortLink);
    }

}
