/*
 * Copyright (C) 2021 hongsan, Inc. All Rights Reserved.
 */
package com.example.shorturl.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.shorturl.domain.ShortUrl;

/**
 * com.example.shorturl.dao shorturl
 */
@Mapper
@Repository
public interface ShortUrlDao {

    public ShortUrl findByShortUrl(String shortUrl);

    public ShortUrl findByLongUrl(String longUrl);

    public void insert(ShortUrl shortUrl);

}
