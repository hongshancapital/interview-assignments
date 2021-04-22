package com.capital.domain.service;

/**
 * @author jiangts
 * @Classname ConversionApplication
 * @Description 域名长短转换
 * @Date 2021/4/19
 * @Version V1.0
 */

public interface IConversionService {
    /**
     * 通过长域名转换短域名,存储并返回
     * @param longDomain 长域名
     * @return
     */
    String langToShort(String longDomain);
    /**
     * 通过短域名获取长域名
     * @param longDomain 长域名
     * @return
     */
    String getLang(String shortDomain);

}
