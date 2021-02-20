package com.example.surl.mapper;

import com.example.surl.entity.UrlDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author 杨欢
 */
@Repository
public interface UrlMapper {

    void insertUrl(@Param("urlDO")UrlDO urlDO);


    UrlDO getUrlByUri(@Param("surl") String surl);


    void deleteUrl(@Param("surl") String surl);
}
