package com.sequoia.service;

import java.util.concurrent.CompletableFuture;

/**
 * ITinyUrlGenerator
 *
 * @author KVLT
 * @date 2022-03-30.
 */
public interface ITinyUrlService {

     /**
      * 根据长域名获取对应的短域名（长域名转码）
      * @param originUrl 长域名
      * @return
      */
     CompletableFuture<String> getTinyUrlFuture(String originUrl);

     /**
      * 根据短域名查询长域名（短域名解析）
      * @param tinyCode 短域名码
      * @return
      */
    String getOriginUrl(String tinyCode);

}
