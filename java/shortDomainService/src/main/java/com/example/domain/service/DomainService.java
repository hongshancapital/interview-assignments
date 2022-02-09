package com.example.domain.service;

/**
 * @title: DomainService
 * @Author DH
 * @Date: 2021/12/6 15:11
 * 域名核心服务
 */
public interface DomainService {

    /**
     * 根据短域名获取长域名
     * @param shortDomain
     * @return
     */
    String getDomain8ShortDomain(String shortDomain);


    /**
     * 存储长域名 返回短域名
     * @param domain
     * @return
     */
    String addDomainInfo(String domain);


}
