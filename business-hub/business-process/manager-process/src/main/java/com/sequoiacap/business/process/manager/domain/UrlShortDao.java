package com.sequoiacap.business.process.manager.domain;

/**
 *@ClassName: UrlShortDao
 *@Description: UrlShortDao
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
public interface UrlShortDao {

  /**
   * 存储对应关系
   * @param url
   * @param shortUrl
   */
  void save(String url, String shortUrl);
  /**
   * 到库里查看是不是存在映射，如果不存在返回null
   * @param shortUrl
   * @return
   */
  String get(String shortUrl);

  /**
   * 清除指定URL的短链接信息
   * @param url
   */
  void clean(String url);
}
