package com.scdt.exam.dao;

import com.scdt.exam.model.ShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShortUrlMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ShortUrl record);

    int insertSelective(ShortUrl record);

    ShortUrl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShortUrl record);

    int updateByPrimaryKey(ShortUrl record);

    ShortUrl selectByCode(@Param("tableName") String tableName, @Param("code") String code);

    void insertWithTableName(ShortUrl sUrl);
}