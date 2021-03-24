package com.wjup.shorturl.mapper;

import com.wjup.shorturl.entity.UrlEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @description: TODO
 * @classname: UrlMapper
 * @author niuxing@huaxiapawn
 * @date 2020/03/21 13:44
*/
@Repository
public interface UrlMapper {

    int createShortUrl(UrlEntity urlEntity);

    UrlEntity findByShortUrlId(String shortUrlId);

    void updateShortUrl(@Param("shortUrlId") String shorlUrlId, @Param("lastView") Date lastView);

    UrlEntity findByPwd(@Param("viewPwd") String viewPwd, @Param("shortUrlId") String shortUrlId);

}
