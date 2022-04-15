package com.scdt.assignment.repository;

import com.xiesx.fastboot.db.jpa.JpaPlusRepository;

/**
 * @title ShortUrlRepository.java
 * @description
 * @author
 * @date 2022-04-15 17:10:47
 */
public interface ShortUrlRepository extends JpaPlusRepository<ShortUrl, Long> {

    /**
     * 通过longHash查找记录
     *
     * @param longHash
     * @return
     */
    ShortUrl findByLongHash(Integer longHash);

    /**
     * 通过shortHash查找记录
     *
     * @param shortHash
     * @return
     */
    ShortUrl findByShortHash(Integer shortHash);
}
