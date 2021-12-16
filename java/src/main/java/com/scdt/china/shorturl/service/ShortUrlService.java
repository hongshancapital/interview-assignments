package com.scdt.china.shorturl.service;

/**
 * 短地址服务
 *
 * @author ng-life
 */
public interface ShortUrlService {

    /**
     * 生成URL对应的短地址
     *
     * @param url URL
     * @return 短地址
     */
    String generate(String url);

    /**
     * 根据短码展开URL
     *
     * @param code 短码
     * @return URL
     */
    String expand(String code);

}
