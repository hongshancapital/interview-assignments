package com.scdt.yulinfu.dao;

import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;
import tk.mybatis.mapper.common.condition.SelectByConditionMapper;
import tk.mybatis.mapper.common.condition.UpdateByConditionSelectiveMapper;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
public interface BaseDao<T> extends SelectByConditionMapper<T>, UpdateByConditionSelectiveMapper<T>, InsertSelectiveMapper<T> {
}
