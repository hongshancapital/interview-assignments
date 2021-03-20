package com.addr.node.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface DomainMapper {

    int insertDomain(Map<String, String> map);

    String queryDomain(String shortDoamin);
}
