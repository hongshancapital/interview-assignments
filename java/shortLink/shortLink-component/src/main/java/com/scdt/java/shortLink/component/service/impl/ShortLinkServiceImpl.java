package com.scdt.java.shortLink.component.service.impl;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.scdt.java.shortLink.component.constant.ServiceException;
import com.scdt.java.shortLink.component.dao.ShortLinkDAO;
import com.scdt.java.shortLink.component.service.ShortLinkService;
import com.scdt.java.shortLink.component.util.ShortLinkUtil;

@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    @Resource
    private ShortLinkUtil shortLinkUtil;
    @Resource
    private ShortLinkDAO shortLinkDAO;

    @Override
    public String getShortLink(String longLink) {
        String existShortLink = shortLinkDAO.getShortFromLong(longLink);
        if (!existShortLink.equals("")) {
            return existShortLink;
        }

        String md5Hex = DigestUtils.md5Hex(longLink);
        for (int i = 0; i < 4; i++) {
            String shortLink = shortLinkUtil.generateShortLink(md5Hex, i);
            if (!shortLinkUtil.existShortLink(shortLink)) {
                shortLinkDAO.saveLinkRelation(longLink, shortLink);
                return shortLink;
            }
        }
        throw new ServiceException("生成短链接冲突");
    }

    @Override
    public String restoreLongLink(String shortLink) {
        String longLink = shortLinkDAO.getLongFromShort(shortLink);
        if (longLink.equals("")) {
            throw new ServiceException("短链接不存在");
        }
        return longLink;
    }
}
