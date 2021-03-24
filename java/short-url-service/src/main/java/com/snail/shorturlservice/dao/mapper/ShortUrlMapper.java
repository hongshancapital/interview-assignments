package com.snail.shorturlservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.shorturlservice.po.ShortUrlPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShortUrlMapper extends BaseMapper<ShortUrlPO> {

    @Select("SELECT t.id,t.short_key,t.long_url,t.long_hash_code,t.seq,t.create_time" +
            "  FROM short_url t" +
            "  WHERE t.short_key = #{shortKey}")
    ShortUrlPO findByShortKey(@Param("shortKey") String shortKey);

    @Select("SELECT t.id,t.short_key,t.long_url,t.long_hash_code,t.seq,t.create_time" +
            "  FROM short_url t" +
            "  WHERE t.long_hash_code = #{longHashCode}")
    List<ShortUrlPO> findByLongHashCode(@Param("longHashCode") Long longHashCode);
}
