package com.alexyuan.shortlink.service;


import com.alexyuan.shortlink.common.variant.CacheVariant;

public interface LinkConvertService {

    /**
     * @Description     短域名生成方法, 用来生成对应的短域名.
     * @param long_link  输入的长域名String
     */
    CacheVariant shortLinkGenerator(String long_link, boolean read_cache);

    /**
     * @Description     长域名的查询方法, 通过短域名来获取对应的长域名
     * @param shortLink  输入的短域名String
     */
    CacheVariant longLinkSearcher(String shortLink);
}
