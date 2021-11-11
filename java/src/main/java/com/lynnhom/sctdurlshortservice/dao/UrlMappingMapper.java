package com.lynnhom.sctdurlshortservice.dao;

import com.lynnhom.sctdurlshortservice.model.po.ShortKey;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

/**
 * @description: Url映射表Dao
 * @author: Lynnhom
 * @create: 2021-11-02 16:47
 **/
public interface UrlMappingMapper {

    @Insert("insert into url_mapping(short_key, original_url, expire_time) " +
            "values (#{shortKey}, #{originalUrl}, #{expireTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @SelectKey(before = false, keyColumn = "id", keyProperty = "id",
            statement = "select last_insert_id()", resultType = Long.class)
    long insert(ShortKey record);

    @Select("select id, short_key, original_url, expire_time, ctime, utime " +
            "from url_mapping where original_url= #{originalUrl}")
    @Results(id = "urlMappingResult", value = {
            @Result(column = "id", property = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(column = "short_key", property = "shortKey", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "original_url", property = "originalUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "expire_time", property = "expireTime", javaType = LocalDateTime.class, jdbcType = JdbcType.DATE),
            @Result(column = "ctime", property = "ctime", javaType = LocalDateTime.class, jdbcType = JdbcType.DATE),
            @Result(column = "utime", property = "utime", javaType = LocalDateTime.class, jdbcType = JdbcType.DATE)
    })
    ShortKey getByOriginalUrl(String originalUrl);

}
