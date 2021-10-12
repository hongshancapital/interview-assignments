package com.jk.shorturl.dao;

import com.jk.shorturl.domain.ShortLongBean;

/**
 * @author Jiang Jikun
 * ShortCode
 * @Description 域名存储接口
 */
public interface ShortDao {

    /**
     * 根据长域名查询出来短域名
     *
     * @param longURL
     * @return null 不存在/ 长域名
     */
    String findShortCode(String longURL);

    /**
     * 从存储中根据短域名获取长域名
     *
     * @param shortCode
     * @return null 不存在/ 长域名
     */
    String findLongURL(String shortCode);

    /**
     * 保存短域名和长域名的对应关系
     *
     * @param shortLongBean
     * @return
     */
    boolean saveShortCode(ShortLongBean shortLongBean);
}
