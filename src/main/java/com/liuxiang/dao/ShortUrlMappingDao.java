package com.liuxiang.dao;

import com.liuxiang.model.po.ShortUrlMappingPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuxiang6
 * @date 2022-01-07
 **/
@Mapper
public interface ShortUrlMappingDao {

    void insert(ShortUrlMappingPo shortUrlMappingPo);

    ShortUrlMappingPo getByShortUrl(@Param("shortUrl") String shortUrl);

    List<ShortUrlMappingPo> getListFromId(@Param("id") long startId);

}
