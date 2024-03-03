package com.tb.link.domain.service;

/**
 * 短链生成 域服务
 * @author andy.lhc
 * @date 2022/4/16 13:34
 */
public interface ShortLinkDomainService {

    /**
     * 根据长链生成短链
     * @param originUrl
     * @return
     */
    String  generateShorLink(String originUrl) ;

    /**
     *
     * @param originUrl
     * @param randomLength
     * @return
     */
    String  generateShortLinkWithRandom(String originUrl,int randomLength) ;

}

