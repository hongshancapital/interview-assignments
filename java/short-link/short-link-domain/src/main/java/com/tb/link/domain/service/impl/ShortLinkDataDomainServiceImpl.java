package com.tb.link.domain.service.impl;

import com.tb.link.domain.model.ShortLinkDomain;
import com.tb.link.domain.model.enums.ErrorCodeEnum;
import com.tb.link.domain.service.ShortLinkDataDomainService;
import com.tb.link.infrastructure.exception.TbRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author andy.lhc
 * @date 2022/4/17 0:01
 */
@Service
public class ShortLinkDataDomainServiceImpl implements ShortLinkDataDomainService {

    /**
     * 暂时写，用DB存时，做分库分表，或存在tair中
     */
    private static final int CAPACITY = 1 << 20;

    /**
     * 本地存储
     */
    private static final Map<String, ShortLinkDomain> store = new ConcurrentHashMap<>(CAPACITY);

    @Override
    public boolean saveShort2LongLink(ShortLinkDomain shortLinkDomain) {
        store.putIfAbsent(shortLinkDomain.getShortLink(), shortLinkDomain);
        return true;
    }


    @Override
    public boolean containShortLink(String shortLink) {
        return StringUtils.isBlank(getLongLink(shortLink))
                ? false
                : true;
    }


    @Override
    public String getLongLink(String shortLink) {
        ShortLinkDomain linkDomain = store.get(shortLink);
        if (Objects.isNull(linkDomain)) {
            throw new TbRuntimeException(ErrorCodeEnum.SHORT_LINK_NOT_FOUND.getErrorCode()
                    , ErrorCodeEnum.SHORT_LINK_NOT_FOUND.getErrorMsg());
        }
        if (new Date().after(linkDomain.getExpireTime())) {
            store.remove(shortLink);
            throw new TbRuntimeException(ErrorCodeEnum.SHORT_LINK_HAS_EXPIRE.getErrorCode()
                    , ErrorCodeEnum.SHORT_LINK_HAS_EXPIRE.getErrorMsg());
        }
        return linkDomain.getOriginLink();
    }

    @Override
    public ShortLinkDomain getOriginLink(String shortLink) {
        return store.get(shortLink);
    }


}
