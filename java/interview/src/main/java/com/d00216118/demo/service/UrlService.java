package com.d00216118.demo.service;

import com.d00216118.demo.model.InfoUrl;

import javax.validation.constraints.NotEmpty;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 2:35 下午 2021/4/2
 **/
public interface UrlService {

    boolean checkUrlExist(String url,Long userId);

    InfoUrl convertToTinyUrl( @NotEmpty InfoUrl infoUrl) throws Exception;

    InfoUrl getUrlByTinyUrl(String tinyUrl, Long userId);


}
