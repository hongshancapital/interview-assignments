package com.hongshan.link.service.service;

import com.hongshan.link.service.repository.LinkDataRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author heshineng
 * created by 2022/8/8
 * link数据转换类业务逻辑
 */
@Service
public class LinkConvertService {

    @Resource
    private LinkDataRepository linkDataRepository;

    /**
     * 根据长链接转换成短链接
     *
     * @param longLink 长链接
     * @return 短连接
     */
    public String getShortLink(String longLink) {
        if (linkDataRepository.isContainLinkByLinkMap(longLink)) {
            return linkDataRepository.getShortLink(longLink);
        }
        return linkDataRepository.createShortLink(longLink);
    }

    /**
     * 根据短链接转换成长链接
     *
     * @param shortLink 短连接
     * @return 长链接
     */
    public String getLongLink(String shortLink) {
        int index = shortLink.lastIndexOf("/");
        String id = shortLink.substring(index + 1);
        if (linkDataRepository.isContainIdByIdMap(id)) {
            return linkDataRepository.getLongLink(id);
        }
        return null;
    }
}
