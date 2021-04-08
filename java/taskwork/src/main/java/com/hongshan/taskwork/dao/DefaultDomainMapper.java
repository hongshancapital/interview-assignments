package com.hongshan.taskwork.dao;

import org.apache.ibatis.annotations.Param;

public interface DefaultDomainMapper {
	
	String queryCodeByDomainName(@Param("domainName")String domainName);
	String queryDomainNameByCode(@Param("code")String code);
}
