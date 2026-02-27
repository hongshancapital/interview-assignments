package com.url.transform.service;

/**
 * @author xufei
 * @Description
 * @date 2021/12/9 6:26 PM
 **/
public interface UrlShorterGenerator<T extends ShorterGetter> {


  /**
   * 产生一个短链接对象
   *
   * @param url
   * @return
   */
  T generate(String url);

}