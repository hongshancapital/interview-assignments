package com.scdt.yulinfu.service;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
public interface DataService {

    /**
     * 获取短链接
     * @param longLink
     * @return
     */
    String getShortLink(String longLink);

    /**
     * 获取长链接
     * @param shortLink
     * @return
     */
    String getLongLink(String shortLink);

}
