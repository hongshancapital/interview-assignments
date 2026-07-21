package com.rad.shortdomainname.service.impl;

import com.rad.shortdomainname.service.DomainDataHolderService;
import com.rad.shortdomainname.service.IdGeneratorService;
import com.rad.shortdomainname.service.ShortDomainService;
import com.rad.shortdomainname.utils.NumberConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.rad.shortdomainname.constants.ConstantPool.SHORT_URL_PREFIX;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: 短域名服务
 * @date 2022-03-19 16:12:15
 */
@Service
public class ShortDomainServiceImpl implements ShortDomainService {

    @Resource
    private DomainDataHolderService domainDataHolderService;

    @Resource
    private IdGeneratorService idGeneratorService;

    @Override
    public String generateShortLink(String longLink) throws Exception {
        Long id = idGeneratorService.generateId();
        if (id == null) {
            throw new Exception("号码已经用完");
        }
        String encode = NumberConvertUtil.encode(id, 62, 11);
        int length = encode.length();
        encode = SHORT_URL_PREFIX + encode.substring(length - 8, length);
        domainDataHolderService.putLongUrl(encode, longLink);
        return encode;
    }

    @Override
    public String findLongLink(String shortLink) {
        String longLink = domainDataHolderService.getLongUrl(shortLink);
        if (StringUtils.isEmpty(longLink)){
            throw new IllegalArgumentException("短链接不合法");
        }
        return longLink;
    }
}
