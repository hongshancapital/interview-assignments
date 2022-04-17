package com.tb.link.domain.service;

import com.tb.link.domain.model.ShortLinkDomain;

/**
 * 短链接存储 域服务
 * @author andy.lhc
 * @date 2022/4/16 13:36
 */
public interface ShortLinkDataDomainService {

    /**
     * 保存一个长短链映射
     * @param shortLinkDomain
     * @return
     */
    boolean  saveShort2LongLink(ShortLinkDomain shortLinkDomain) ;

    /**
     * 判断短链是否存在
     * @param shortLink
     * @return
     */
    boolean  containShortLink(String shortLink) ;


    /**
     * 短链取长链
     * @param shortLink
     * @return
     */
    String  getLongLink(String shortLink) ;

    /**
     *
     * @param shortLink
     * @return
     */
    ShortLinkDomain  getOriginLink(String shortLink) ;

}
