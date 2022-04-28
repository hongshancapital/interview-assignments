package com.ittest.service;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/20 17:50
 * @Description:
 */
public interface DomainService {


    /**
     * 接受长域名信息，返回短域名信息
     *
     * @param longDomain 长连接
     * @return 返回值
     */
    String storage(String longDomain);


    /**
     * 接受短域名信息，返回长域名信息。
     *
     * @param shortDomain 短连接
     * @return 返回值
     */
    String transfor(String shortDomain);


}
