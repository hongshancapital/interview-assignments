package com.sequoiacap.domain.dao;

import com.sequoiacap.domain.entity.DomainConf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DomainConfDao {

    List<DomainConf> selectAll();

    DomainConf selectByDomain(@Param("domain") String domain);
}