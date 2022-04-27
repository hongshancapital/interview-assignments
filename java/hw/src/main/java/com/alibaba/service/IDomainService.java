package com.alibaba.service;

/**
 * @author caozx
 * @desc
 * @date 2022年04月19日 15:56
 */
public interface IDomainService {

    String getShortDomain(String url);

    String getLongDomain(String url);
}
