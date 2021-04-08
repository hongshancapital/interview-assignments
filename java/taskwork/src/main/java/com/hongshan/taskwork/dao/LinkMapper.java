package com.hongshan.taskwork.dao;

import org.apache.ibatis.annotations.Param;

import com.hongshan.taskwork.model.Link;

public interface LinkMapper {

	int insert(Link link);
	String queryById(@Param("id")String id);
	String queryLongUrlByShortUrl(@Param("prefixDomainType")String prefixDomainType,@Param("shortUrlSuffix") String shortUrlSuffix);
}
