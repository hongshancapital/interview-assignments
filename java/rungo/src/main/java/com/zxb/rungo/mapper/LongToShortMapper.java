package com.zxb.rungo.mapper;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;

import com.zxb.rungo.entity.LongToShort;

@Mapper
public interface LongToShortMapper {
    int delete(Integer id);

    int insert(HashMap<String, Object> map);

    LongToShort select(HashMap<String, Object> map);

    int update(HashMap<String, Object> map);
}