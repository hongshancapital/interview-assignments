package com.sequoiacap.domain.dao;

import com.sequoiacap.domain.entity.UrlMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMapDao{

    int insertSelective(UrlMap record);

    List<UrlMap> selectAll();

    UrlMap selectByCompressionCode(@Param("compressionCode") String compressionCode);
}