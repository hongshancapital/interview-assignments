package com.sxg.shortUrl.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sxg.shortUrl.model.UrlModel;


/**
 * 
 * @author sxg
 *
 */
@Repository
public interface UrlMapper {

    void insertUrl(@Param("urlModel")UrlModel urlModel);


    UrlModel getUrlByUri(@Param("shortUrl") String shortUrl);


    void deleteUrl(@Param("shortUrl") String shortUrl);
}
