package com.url.transform.service;

/**
 * @author xufei
 * @Description
 * @date 2021/12/9 6:22 PM
 **/
public interface ShorterStorage<T extends ShorterGetter> {

  String get(String shorter);

  String getShorter(String shorter);

  void clean(String url);

  void cleanShorter(String shorter);

  void save(String url, T shorter);

  void clean();

}