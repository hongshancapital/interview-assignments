package com.gc.shorturl.mapper;

import com.gc.shorturl.entity.ShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Author: GC
 * @Date: 2021/3/4
 */
@Mapper
public interface UrlMapper {

    int insert(ShortUrl entity);

    List<ShortUrl> getByShortUrl(@Param("shortUrl") String shortUrl);

    ShortUrl getByShortUrlAndSuffix(@Param("shortUrl") String shortUrl, @Param("suffix") String suffix);

    ShortUrl getByAllUrl(@Param("shortUrl") String shortUrl, @Param("originalUrl") String originalUrl);

    int getMaxIndexByShortUrl(@Param("shortUrl") String shortUrl);
}
