package com.moonciki.interview.commons.tools;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CustomBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}