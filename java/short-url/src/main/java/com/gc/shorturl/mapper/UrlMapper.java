package com.gc.shorturl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Author: GC
 * @Date: 2021/3/4
 */
@Mapper
public interface UrlMapper {

    int insert(@Param("shortUrl") String shortUrl, @Param("originalUrl") String originalUrl);

    String getByShortUrl(@Param("shortUrl") String shortUrl);
}
