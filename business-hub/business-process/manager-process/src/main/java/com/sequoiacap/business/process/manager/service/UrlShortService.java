package com.sequoiacap.business.process.manager.service;

/**
 *@ClassName: UrlShortService
 *@Description: UrlShortService
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
public interface UrlShortService {


  /**
   * 返回指定地址对应的短链接
   * @param url
   * @return
   */
  String get(String url);

  /**
   * 据地址产生短地址
   * @param url
   * @return
   */
  String generate(String url);

}
