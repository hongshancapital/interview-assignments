package com.wjup.shorturl.mapper;

import com.wjup.shorturl.entity.UrlEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Create by wjup on 2019/9/29 11:33
 */

@Repository
public interface UrlMapper {

    int createShortUrl(UrlEntity urlEntity);

    UrlEntity findByShortUrlId(String shortUrlId);

    void updateShortUrl(@Param("shortUrlId") String shorlUrlId, @Param("lastView") Date lastView);

    UrlEntity findByPwd(@Param("viewPwd") String viewPwd, @Param("shortUrlId") String shortUrlId);

}
