package com.scc.base.mapper;

import com.scc.base.entity.UrlDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author renyunyi
 * @date 2022/4/25 11:42 AM
 * @description URL mapper
 **/
@Mapper
public interface UrlMapper {

    /**
     * get id by long url
     * @param urlDO url table data object
     * @return database id, it may does not exist!
     */
    Long getIdByLongUrl(@Param("urlDO") UrlDO urlDO);

    /**
     * get long url by id
     * @param urlDO url table data object
     * @return long url
     */
    String getLongUrlById(@Param("urlDO") UrlDO urlDO);

    /**
     * add new long url and get id
     * @param urlDO url table data object
     * @return id
     */
    long addLongUrl(@Param("urlDO") UrlDO urlDO);

}
