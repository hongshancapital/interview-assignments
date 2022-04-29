package com.interview.wph.shorturl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interview.wph.shorturl.entity.ShortUrlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.List;

@Mapper
public interface ShortUrlMapper extends BaseMapper<ShortUrlEntity> {

    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 100)
    @Select("select short_url_id from t_short_url")
    List<Long> listAllShortId();
}
