package com.scdt.china.shortdomain.service;

import com.scdt.china.shortdomain.utils.Result;

/**
 * @Author: CZ
 * @Date: 2022/1/22 12:10
 * @Description:
 */
public interface DomainService {
    /**
     * 短链接转换为长链接
     *
     * @param longDomain 长链接域名参数
     * @return 短链接域名
     */
    Result<String> getShortDomain(String longDomain);

    /**
     * 长链接转换为短链接
     *
     * @param shortDomain 短链接域名参数
     * @return 长链接域名
     */
    Result<String> getLongDomain(String shortDomain);
}
