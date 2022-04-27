package com.scdt.service.impl;

import com.scdt.dao.ShortLinkDao;
import com.scdt.domin.ErrorCode;
import com.scdt.domin.po.ShortLinkInfo;
import com.scdt.exception.BusinessException;
import com.scdt.service.LinkService;
import com.scdt.util.ConvertUtil;
import com.scdt.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LinkServiceImpl
 *
 * @author weixiao
 * @date 2022-04-26 13:44
 */
@Service
public class LinkServiceImpl implements LinkService {
    /** 短链接固定的字符长度 */
    private static final int FIX_LENGTH = 8;

    @Autowired
    private ShortLinkDao shortLinkDao;

    @Override
    public String createShortLink(String longLink) throws BusinessException {
        long id = IdUtil.generateUniqueId();
        String shortLink = ConvertUtil.tenToSixtyTwo(id, FIX_LENGTH);
        if (StringUtils.length(shortLink) > FIX_LENGTH) {
            throw new BusinessException(ErrorCode.SERVER_ERROR, "无可用资源");
        }
        shortLinkDao.save(buildShortLinkInfo(shortLink, longLink));
        return shortLink;
    }

    private ShortLinkInfo buildShortLinkInfo(String shortLink, String longLink) {
        ShortLinkInfo shortLinkInfo = new ShortLinkInfo();
        shortLinkInfo.setShortLink(shortLink);
        shortLinkInfo.setLongLink(longLink);
        return shortLinkInfo;
    }

    @Override
    public String getLongLink(String shortLink) throws BusinessException {
        String longLink = shortLinkDao.getLongLink(shortLink);
        if (longLink == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "无对应长域名信息");
        }
        return longLink;
    }
}
