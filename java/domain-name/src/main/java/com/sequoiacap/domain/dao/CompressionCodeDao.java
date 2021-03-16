package com.sequoiacap.domain.dao;


import com.sequoiacap.domain.entity.CompressionCode;
import org.springframework.stereotype.Repository;

@Repository
public interface CompressionCodeDao{

    CompressionCode getLatestAvailableCompressionCode();

    int deleteByPrimaryKey(Long id);

    int insertSelective(CompressionCode record);

    CompressionCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompressionCode record);

}