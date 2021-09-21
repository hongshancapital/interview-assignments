package com.domainserver.service;

import com.domaincore.exceptions.BusinessException;

/**
 * 短域名服务实接口类
 *
 * @author 905393944@qq.com
 * @Date 2021/9/21
 */
public interface IShortDomainService {
    /**
     * 功能描述: 通过长域名获取短域名
     *
     * @param longDomain 长域名
     * @return java.lang.String
     * @throws Exception
     * @author 905393944@qq.com
     * @Date 2021/9/21
     */
    String getShortDomain(String longDomain) throws BusinessException;

    /**
     * 功能描述: 通过短域名获取长域名
     *
     * @param shortDomain 短域名
     * @return java.lang.String
     * @throws Exception
     * @author 905393944@qq.com
     * @Date 2021/9/21
     */
    String getLongDomain(String shortDomain);
}
