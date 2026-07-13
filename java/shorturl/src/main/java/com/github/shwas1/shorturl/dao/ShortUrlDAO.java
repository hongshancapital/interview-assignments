package com.github.shwas1.shorturl.dao;

import com.github.shwas1.shorturl.model.ShortUrlPO;

/**
 * 短路径DAO接口，方便后续替换底层存储实现
 */
public interface ShortUrlDAO {
    /**
     * 保存短链接信息
     */
    ShortUrlPO save(ShortUrlPO shortUrlPO);

    /**
     * 通过短路径查找短链接信息
     *
     * @param shortPath 短路径
     */
    ShortUrlPO getByShortUrl(String shortPath);


}
